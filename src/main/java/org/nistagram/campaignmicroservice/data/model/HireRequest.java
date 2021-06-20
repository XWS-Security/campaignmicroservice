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

    @Column(name = "influencer_id")
    private Long influencerId;

    @Column(name = "approval_status")
    private ApprovalStatus approvalStatus;

    public HireRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInfluencerId() {
        return influencerId;
    }

    public void setInfluencerId(Long influencerId) {
        this.influencerId = influencerId;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    //TODO: different logic?
    public void approve(){
        this.approvalStatus=ApprovalStatus.APPROVED;
    }


    public void reject(){
        this.approvalStatus=ApprovalStatus.REJECTED;
    }
}
