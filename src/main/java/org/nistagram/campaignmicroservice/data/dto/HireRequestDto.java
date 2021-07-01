package org.nistagram.campaignmicroservice.data.dto;

import org.nistagram.campaignmicroservice.data.enums.ApprovalStatus;

public class HireRequestDto {

    private String influencerUsername;
    private ApprovalStatus approvalStatus;
    private Long campaignId;

    public HireRequestDto() {
    }

    public HireRequestDto(String influencerUsername, ApprovalStatus approvalStatus, Long campaignId) {
        this.influencerUsername = influencerUsername;
        this.approvalStatus = approvalStatus;
        this.campaignId = campaignId;
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

}
