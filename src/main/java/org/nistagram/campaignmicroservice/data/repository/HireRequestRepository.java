package org.nistagram.campaignmicroservice.data.repository;

import org.nistagram.campaignmicroservice.data.model.HireRequest;
import org.springframework.data.repository.CrudRepository;

public interface HireRequestRepository extends CrudRepository<HireRequest, Long> {
}
