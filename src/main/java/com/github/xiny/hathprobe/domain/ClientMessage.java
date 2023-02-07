package com.github.xiny.hathprobe.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClientMessage extends Client {


       private String trust;
       private String quality;
       private String hitrate;
       private String hathrate;

       public ClientMessage(Integer id, String name, String status, String clientTrust, String clientQuality, String clientHitrate, String clientHathrate) {
              super(id, name, status);
              this.trust = clientTrust;
              this.quality = clientQuality;
              this.hitrate = clientHitrate;
              this.hathrate = clientHathrate;
       }
}
