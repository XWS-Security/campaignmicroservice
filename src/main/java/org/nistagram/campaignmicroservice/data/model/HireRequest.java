package org.nistagram.campaignmicroservice.data.model;

import org.nistagram.campaignmicroservice.data.enums.ApprovalStatus;

import javax.persistence.*;

@Entity
@Table(name = "hire_request")
public class HireRequest {

    @Id
    @SequenceGenerator(name = "hire_request_sequence_generator", sequenceName = "hire_request_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hire_request_sequence_generator")
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "influencer_username")
    private String influencerUsername;

    @Column(name = "approval_status")
    private ApprovalStatus approvalStatus;

    public HireRequest() {
    }

    public HireRequest(String influencerUsername, ApprovalStatus approvalStatus) {
        this.influencerUsername = influencerUsername;
        this.approvalStatus = approvalStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfluencerUsername() {
        return influencerUsername;
    }

    public void setInfluencerUsername(String influencerUsername) {
        this.influencerUsername = influencerUsername;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public void approve(){
        this.approvalStatus=ApprovalStatus.APPROVED;
    }

    public void reject(){
        this.approvalStatus=ApprovalStatus.REJECTED;
    }
}
