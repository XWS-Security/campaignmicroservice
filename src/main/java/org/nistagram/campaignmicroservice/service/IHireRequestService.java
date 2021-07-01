package org.nistagram.campaignmicroservice.service;

import org.nistagram.campaignmicroservice.data.dto.HireRequestDto;

public interface IHireRequestService {
    void create(HireRequestDto dto);
}
