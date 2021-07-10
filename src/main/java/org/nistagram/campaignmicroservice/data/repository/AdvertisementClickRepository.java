package org.nistagram.campaignmicroservice.data.repository;

import org.nistagram.campaignmicroservice.data.model.AdvertisementClick;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdvertisementClickRepository extends CrudRepository<AdvertisementClick, Long> {
    List<AdvertisementClick> findAllByCampaign_Id(Long id);
}
