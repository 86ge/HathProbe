package com.github.xiny.hathprobe.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.github.xiny.hathprobe.domain.AjaxJson;
import com.github.xiny.hathprobe.domain.Client;
import com.github.xiny.hathprobe.domain.ProbedMessage;
import com.github.xiny.hathprobe.mapper.ProbedMessageMapper;
import com.github.xiny.hathprobe.service.HathService;
import com.github.xiny.hathprobe.service.ProbedMessageService;
import com.github.xiny.hathprobe.service.impl.ClientServiceImpl;
import com.github.xiny.hathprobe.service.impl.ProbedMessageServiceImpl;
import com.github.xiny.hathprobe.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class Controller {

    @Resource
    private UserServiceImpl userService;

    @PostMapping("/login")
    public AjaxJson login(@RequestParam String userName, @RequestParam String password) {
        try {
            userService.login(userName, password);
        } catch (IllegalArgumentException e) {
            return AjaxJson.getError(e.getMessage());
        }
        return AjaxJson.getSuccess("登录成功", StpUtil.getTokenValue());
    }

    @Resource
    private HathService hathService;

    @RequestMapping("/getMessage")
    public AjaxJson getMessage(@RequestParam int clientId,@RequestParam int intervalTime) {
        List<Object> message = Collections.singletonList(hathService.computeHathRate(clientId, intervalTime));
        return AjaxJson.getSuccessData(message);
    }

    @Resource
    private ClientServiceImpl clientService;

    @RequestMapping("/getClients")
    public AjaxJson getClients() {
        List<Client> clients = clientService.list();
        return AjaxJson.getSuccessData(clients);
    }
}
