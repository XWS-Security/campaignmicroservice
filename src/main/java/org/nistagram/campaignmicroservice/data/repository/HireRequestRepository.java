package org.nistagram.campaignmicroservice.data.repository;

import org.nistagram.campaignmicroservice.data.model.HireRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HireRequestRepository extends CrudRepository<HireRequest, Long> {
    @Query(value = "SELECT distinct id, approval_status, influencer_username, campaign_id FROM hire_request where influencer_username=:username and approval_status=0", nativeQuery = true)
    List<HireRequest> findAllByInfluencerUsernameAndWaitingStatus(String username);

    @Query(value = "SELECT distinct id, approval_status, influencer_username, campaign_id FROM hire_request where influencer_username=:username and campaign_id=:campaignId", nativeQuery = true)
    List<HireRequest> findAllByInfluencersUsernameCampaignId(String username, Long campaignId);

    @Query(value = "SELECT distinct id, approval_status, influencer_username, campaign_id FROM hire_request where influencer_username=:username and campaign_id=:campaignId and approval_status=1", nativeQuery = true)
    List<HireRequest> findAllByInfluencersUsernameCampaignIdAndApproved(String username, Long campaignId);

}
