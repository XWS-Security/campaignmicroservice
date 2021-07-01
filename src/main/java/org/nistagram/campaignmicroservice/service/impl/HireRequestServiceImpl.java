package org.nistagram.campaignmicroservice.service.impl;

import org.nistagram.campaignmicroservice.data.dto.HireRequestDto;
import org.nistagram.campaignmicroservice.data.model.HireRequest;
import org.nistagram.campaignmicroservice.data.repository.CampaignRepository;
import org.nistagram.campaignmicroservice.data.repository.HireRequestRepository;
import org.nistagram.campaignmicroservice.exceptions.NotFoundException;
import org.nistagram.campaignmicroservice.service.IHireRequestService;
import org.springframework.stereotype.Service;

@Service
public class HireRequestServiceImpl implements IHireRequestService {
    private final CampaignRepository campaignRepository;
    private final HireRequestRepository hireRequestRepository;

    public HireRequestServiceImpl(CampaignRepository campaignRepository, HireRequestRepository hireRequestRepository) {
        this.campaignRepository = campaignRepository;
        this.hireRequestRepository = hireRequestRepository;
    }

    @Override
    public void create(HireRequestDto dto) {
        var hireRequest = new HireRequest(dto.getInfluencerUsername(),dto.getApprovalStatus());
        hireRequestRepository.save(hireRequest);
        var optionalCampaign = campaignRepository.findById(dto.getCampaignId());
        if(optionalCampaign.isEmpty()){
            throw new NotFoundException();
        }
        var campaign = optionalCampaign.get();
        campaign.addHireRequest(hireRequest);
        campaignRepository.save(campaign);
    }
}
