package org.nistagram.campaignmicroservice.data.repository;

import org.nistagram.campaignmicroservice.data.model.AdvertisementClick;
import org.springframework.data.repository.CrudRepository;

public interface AdvertisementClickRepository extends CrudRepository<AdvertisementClick, Long> {
}
