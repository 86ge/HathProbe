package com.github.xiny.hathprobe.domain;

import lombok.Data;

@Data
public class ConnectMessage {

    private String ipb_member_id;

    private String ipb_pass_hash;

    private String proxy_host;

    private String proxy_port;

    public ConnectMessage(String ipb_member_id, String ipb_pass_hash) {
        this.ipb_member_id = ipb_member_id;
        this.ipb_pass_hash = ipb_pass_hash;
    }

    public ConnectMessage(String ipb_member_id, String ipb_pass_hash, String proxy_host, String proxy_port) {
        this.ipb_member_id = ipb_member_id;
        this.ipb_pass_hash = ipb_pass_hash;
        this.proxy_host = proxy_host;
        this.proxy_port = proxy_port;
    }
}
