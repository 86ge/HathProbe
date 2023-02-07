package com.github.xiny.hathprobe.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "hath")
public class HathConfig {

    private String ipb_member_id;

    private String ipb_pass_hash;

    private String proxy_host;

    private String proxy_port;

}
