package org.nistagram.campaignmicroservice.data.dto;

import org.nistagram.campaignmicroservice.data.enums.ApprovalStatus;

import java.io.Serializable;

public class HireRequestDto implements Serializable {

    private String influencerUsername;
    private ApprovalStatus approvalStatus;
    private Long campaignId;
    private String agentUsername;
    private Long id;

    public HireRequestDto() {
    }

    public HireRequestDto(String influencerUsername, ApprovalStatus approvalStatus, Long campaignId, String agentUsername,  Long id) {
        this.influencerUsername = influencerUsername;
        this.approvalStatus = approvalStatus;
        this.campaignId = campaignId;
        this.agentUsername = agentUsername;
        this.id = id;
    }

    public String getInfluencerUsername() {
        return influencerUsername;
    }

    public void setInfluencerUsername(String influencerId) {
        this.influencerUsername = influencerId;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getAgentUsername() {
        return agentUsername;
    }

    public void setAgentUsername(String agentUsername) {
        this.agentUsername = agentUsername;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
