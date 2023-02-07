package com.github.xiny.hathprobe.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName probed_message
 */
@TableName(value ="probed_message")
@Data
public class ProbedMessage implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer msgId;

    /**
     * 
     */
    private Integer clientId;

    /**
     * 
     */
    private Date timestamp;

    /**
     * 
     */
    private Integer trust;

    /**
     * 
     */
    private Integer quality;

    /**
     * 
     */
    private String hitrate;

    /**
     * 
     */
    private String hathrate;

    public ProbedMessage(Integer msgId, Integer clientId, Date timestamp, Integer trust, Integer quality, String hitrate, String hathrate) {
        this.msgId = msgId;
        this.clientId = clientId;
        this.timestamp = timestamp;
        this.trust = trust;
        this.quality = quality;
        this.hitrate = hitrate;
        this.hathrate = hathrate;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ProbedMessage other = (ProbedMessage) that;
        return (this.getMsgId() == null ? other.getMsgId() == null : this.getMsgId().equals(other.getMsgId()))
            && (this.getClientId() == null ? other.getClientId() == null : this.getClientId().equals(other.getClientId()))
            && (this.getTimestamp() == null ? other.getTimestamp() == null : this.getTimestamp().equals(other.getTimestamp()))
            && (this.getTrust() == null ? other.getTrust() == null : this.getTrust().equals(other.getTrust()))
            && (this.getQuality() == null ? other.getQuality() == null : this.getQuality().equals(other.getQuality()))
            && (this.getHitrate() == null ? other.getHitrate() == null : this.getHitrate().equals(other.getHitrate()))
            && (this.getHathrate() == null ? other.getHathrate() == null : this.getHathrate().equals(other.getHathrate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMsgId() == null) ? 0 : getMsgId().hashCode());
        result = prime * result + ((getClientId() == null) ? 0 : getClientId().hashCode());
        result = prime * result + ((getTimestamp() == null) ? 0 : getTimestamp().hashCode());
        result = prime * result + ((getTrust() == null) ? 0 : getTrust().hashCode());
        result = prime * result + ((getQuality() == null) ? 0 : getQuality().hashCode());
        result = prime * result + ((getHitrate() == null) ? 0 : getHitrate().hashCode());
        result = prime * result + ((getHathrate() == null) ? 0 : getHathrate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", msgId=").append(msgId);
        sb.append(", clientId=").append(clientId);
        sb.append(", timestamp=").append(timestamp);
        sb.append(", trust=").append(trust);
        sb.append(", quality=").append(quality);
        sb.append(", hitrate=").append(hitrate);
        sb.append(", hathrate=").append(hathrate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}