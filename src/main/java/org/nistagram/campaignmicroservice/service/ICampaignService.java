package org.nistagram.campaignmicroservice.service;

import org.nistagram.campaignmicroservice.data.dto.CampaignDto;

import java.util.List;

public interface ICampaignService {
    void create(CampaignDto dto);

    void update(CampaignDto dto);

    void delete(Long id);

    List<CampaignDto> getAllContinuous();
}
