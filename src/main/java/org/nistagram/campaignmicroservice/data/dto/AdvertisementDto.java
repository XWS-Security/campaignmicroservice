package org.nistagram.campaignmicroservice.data.dto;

import java.io.Serializable;
import java.util.Date;

public class AdvertisementDto implements Serializable {
    private Date currentMoment;
    private Long contentId;
    private String link;
    private String agentAccountUsername;
    private Long campaignId;

    public AdvertisementDto() {
    }

    public AdvertisementDto(Date currentMoment, Long contentId, String link, String agentAccountUsername, Long campaignId) {
        this.currentMoment = currentMoment;
        this.contentId = contentId;
        this.link = link;
        this.agentAccountUsername = agentAccountUsername;
        this.campaignId = campaignId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentid) {
        this.contentId = contentId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Date getCurrentMoment() {
        return currentMoment;
    }

    public void setCurrentMoment(Date currentMoment) {
        this.currentMoment = currentMoment;
    }

    public String getAgentAccountUsername() {
        return agentAccountUsername;
    }

    public void setAgentAccountUsername(String agentAccountUsername) {
        this.agentAccountUsername = agentAccountUsername;
    }
}
