package org.nistagram.campaignmicroservice.data.repository;

import org.nistagram.campaignmicroservice.data.model.Campaign;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CampaignRepository extends CrudRepository<Campaign, Long> {
    List<Campaign> findAllByAgentAccountUsername(String id);

    @Query(value = "SELECT c.campaign_type, c.id, c.agent_account_username, c.deleted, c.gender, c.max_age, c.min_age, c.exposure_end, c.exposure_start, c.last_update, c.required_daily_displays, c.exposure_date, c.advertisement_id" +
            " FROM hire_request as hr, campaign as c where c.id=hr.campaign_id", nativeQuery = true)
    Campaign findByHireRequestId(Long id);
}
