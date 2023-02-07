package com.github.xiny.hathprobe.service;

import com.github.xiny.hathprobe.config.HathConfig;
import com.github.xiny.hathprobe.domain.ConnectMessage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AfterRunner implements ApplicationRunner {

    @Resource
    private HathConfig hathConfig;

    @Resource
    private HathService hathService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("checking cookie......");
        String ipb_member_id = hathConfig.getIpb_member_id();
        String ipb_pass_hash = hathConfig.getIpb_pass_hash();
        if (ipb_member_id == null || ipb_pass_hash == null) {
            log.error("cookie not found, please check your config file");
            System.exit(1);
        }
        log.info("ipb_member_id:" + ipb_member_id);
        log.info("ipb_pass_hash:" + ipb_pass_hash);
        ConnectMessage message = new ConnectMessage(ipb_member_id, ipb_pass_hash,hathConfig.getProxy_host(),hathConfig.getProxy_port());
        Element hathPage = hathService.getHathPage(message);
        hathService.setMessage(message);
    }
}

