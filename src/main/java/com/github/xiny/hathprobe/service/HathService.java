package com.github.xiny.hathprobe.service;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.github.xiny.hathprobe.domain.Client;
import com.github.xiny.hathprobe.domain.ClientMessage;
import com.github.xiny.hathprobe.domain.ConnectMessage;
import com.github.xiny.hathprobe.domain.ProbedMessage;
import com.github.xiny.hathprobe.mapper.ProbedMessageMapper;
import com.github.xiny.hathprobe.service.impl.ClientServiceImpl;
import com.github.xiny.hathprobe.service.impl.ProbedMessageServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class HathService {

    @Resource
    private ProbedMessageServiceImpl probedMessageService;
    @Resource
    private ClientServiceImpl clientService;

    private ConnectMessage message;

    public void setMessage(ConnectMessage message) {
        this.message = message;
    }

    public Element getHathPage(ConnectMessage message) throws IOException {
        Connection connection = Jsoup.connect("https://e-hentai.org/hentaiathome.php")
                .cookie("ipb_member_id", message.getIpb_member_id())
                .cookie("ipb_pass_hash", message.getIpb_pass_hash());
        if (message.getProxy_host() != null && message.getProxy_port() != null) {
            connection.proxy(message.getProxy_host(), Integer.parseInt(message.getProxy_port()));
        }
        return connection.get().body();
    }
    @Scheduled(cron = "0/60 * * * * ?")
    public void probeHath() {
        log.info("Probing HathMessage......");
        Element hathPage = null;
        try {
            hathPage = getHathPage(message);
        } catch (IOException e) {
            log.error("Error when connected E-hentai");
            System.exit(1);
        }
        Elements children = hathPage.getElementsByClass("hct").get(0)
                .getElementsByTag("tbody").get(0)
                .children();
        children.remove(0);
        List<Client> clientList = new ArrayList<>();
        for (Element child : children) {
            Elements msg = child.children();
            String clientName = msg.get(0).text();
            String clientId = msg.get(1).text();
            String clientStatus = msg.get(2).text();
            if (!clientStatus.equals("Online")) {
                clientList.add(new Client( Integer.parseInt(clientId), clientName,clientStatus));
                continue;
            }
            String clientTrust = msg.get(10).text();
            String clientQuality = msg.get(11).text();
            String clientHitrate = msg.get(12).text();
            String clientHathrate = msg.get(13).text();
            clientList.add(new ClientMessage( Integer.parseInt(clientId),clientName, clientStatus, clientTrust, clientQuality, clientHitrate, clientHathrate));
        }
        processingMessage(clientList);
    }

    @Resource
    private ProbedMessageMapper probedMessageMapper;

    public void processingMessage(List<Client> msg) {
        for (Client clientMessage : msg) {
            if (clientMessage.getStatus().equals("Online") && clientMessage instanceof ClientMessage cM) {
                Client client = clientService.getById(cM.getId());
                if (client ==null || client.getName() == null || client.getId() == null) {
                    clientService.save(cM);
                }else if (!Objects.equals(client.getStatus(), cM.getStatus())) {
                    cM.setStatus(client.getStatus());
                    clientService.save(cM);
                }
                ProbedMessage probedMessage = new ProbedMessage(null, cM.getId(), new Date(System.currentTimeMillis()), Integer.parseInt(cM.getTrust()), Integer.parseInt(cM.getQuality()), cM.getHitrate(), cM.getHathrate());
                probedMessageService.save(probedMessage);
            }
        }
    }

    public List<Map<String, Map<String, Integer>>> computeHathRate(int clientId, int intervalTime) {

        int ceil = (int) Math.ceil(intervalTime / 20.0);

        List<Long> longList = splitTime(DateUtil.offset(new Date(), DateField.MINUTE, -intervalTime).getTime(), System.currentTimeMillis(), 1000L * 60 * ceil);

        longList = longList.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());

        List<ProbedMessage> msgList;
        switch (intervalTime) {
            case 10 -> msgList = probedMessageMapper.getMessage(clientId, 10);
            case 30 -> msgList = probedMessageMapper.getMessage(clientId, 30);
            case 60 -> msgList = probedMessageMapper.getMessage(clientId, 60);
            case 360 -> msgList = probedMessageMapper.getMessage(clientId, 360);
            case 720 -> msgList = probedMessageMapper.getMessage(clientId, 720);
            case 1440 -> msgList = probedMessageMapper.getMessage(clientId, 1440);
            default -> throw new IllegalArgumentException("intervalTime not support");
        }

        List<Map<String,Map<String,Integer>>> subList = new ArrayList<>();
        longList.forEach(longTime -> subList.add(new HashMap<>(){{
            put(DateUtil.format(new Date(longTime), "HH:mm"),new HashMap<>(){{
                put("trust",0);
                put("quality",0);
                put("count",0);
            }});
        }}));

        for (ProbedMessage probedMessage : msgList) {
            Date messageTimestamp = probedMessage.getTimestamp();
            for (Long aLong : longList) {
                if (messageTimestamp.getTime() < aLong && (messageTimestamp.getTime() > aLong - 1000L * 60 * ceil)) {
                    Map<String, Integer> integerMap = subList.get(longList.indexOf(aLong)).get(DateUtil.format(new Date(aLong), "HH:mm"));
                    integerMap.put("trust", probedMessage.getTrust()+integerMap.get("trust"));
                    integerMap.put("quality", probedMessage.getQuality()+integerMap.get("quality"));
                    integerMap.put("count", integerMap.get("count") + 1);
                    break;
                }
            }
        }
        for (Map<String, Map<String, Integer>> map : subList) {
            Map<String, Integer> stringIntegerMap = map.get(DateUtil.format(new Date(longList.get(subList.indexOf(map))), "HH:mm"));
            if (stringIntegerMap.get("count") != 0) {
                stringIntegerMap.put("trust", stringIntegerMap.get("trust") / stringIntegerMap.get("count"));
                stringIntegerMap.put("quality", stringIntegerMap.get("quality") / stringIntegerMap.get("count"));
            }
        }
        return subList;
    }

    public List<Long> splitTime(long startTime, long endTime, long timeLong) {
        ArrayList<Long> timeList = new ArrayList<>();
        for (long i = startTime; i < endTime;i = i+ timeLong){
            timeList.add(i+timeLong);
        }
        return timeList;
    }

}
