package org.nistagram.campaignmicroservice.data.repository;

import org.nistagram.campaignmicroservice.data.model.ContinuousCampaign;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContinuousCampaignRepository extends CrudRepository<ContinuousCampaign, Long> {
    List<ContinuousCampaign> findAllByAgentAccountUsername(String id);
    List<ContinuousCampaign> findAllByDeletedFalseAndMinAgeLessThanEqualAndMaxAgeGreaterThanEqual(Integer age1, Integer age2);
}
