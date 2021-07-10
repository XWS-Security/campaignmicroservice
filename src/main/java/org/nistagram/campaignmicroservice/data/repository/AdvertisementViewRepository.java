package org.nistagram.campaignmicroservice.data.repository;

import org.nistagram.campaignmicroservice.data.model.AdvertisementView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdvertisementViewRepository extends CrudRepository<AdvertisementView, Long> {

    @Query(value = "SELECT content_owner_username, id, username, campaign_id FROM advertisement_view WHERE username=:username and campaign_id=:campaignId",  nativeQuery = true)
    List<AdvertisementView> findAllByUsernameAndCampaignsId(String username, Long campaignId);

    List<AdvertisementView> findAllByCampaign_Id(Long id);
}
