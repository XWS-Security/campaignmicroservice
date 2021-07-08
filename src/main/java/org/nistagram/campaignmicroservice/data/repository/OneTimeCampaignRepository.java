package org.nistagram.campaignmicroservice.data.repository;

import org.nistagram.campaignmicroservice.data.model.OneTimeCampaign;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OneTimeCampaignRepository extends CrudRepository<OneTimeCampaign, Long> {
    List<OneTimeCampaign> findAllByDeletedFalseAndMinAgeLessThanEqualAndMaxAgeGreaterThanEqual(Integer age1, Integer age2);
}
