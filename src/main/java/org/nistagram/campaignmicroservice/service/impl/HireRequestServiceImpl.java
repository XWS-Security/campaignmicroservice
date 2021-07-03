package org.nistagram.campaignmicroservice.service.impl;

import org.nistagram.campaignmicroservice.data.dto.HireRequestDto;
import org.nistagram.campaignmicroservice.data.enums.ApprovalStatus;
import org.nistagram.campaignmicroservice.data.model.HireRequest;
import org.nistagram.campaignmicroservice.data.repository.CampaignRepository;
import org.nistagram.campaignmicroservice.data.repository.HireRequestRepository;
import org.nistagram.campaignmicroservice.exceptions.NotFoundException;
import org.nistagram.campaignmicroservice.exceptions.RequestAlreadyCreatedException;
import org.nistagram.campaignmicroservice.service.IHireRequestService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        var oldRequests = hireRequestRepository.findAllByInfluencersUsernameCampaignId(dto.getInfluencerUsername(), dto.getCampaignId());
        if(oldRequests.size()!=0){
            throw new RequestAlreadyCreatedException();
        }
        var hireRequest = new HireRequest(dto.getInfluencerUsername(), ApprovalStatus.WAITING);
        hireRequestRepository.save(hireRequest);
        var optionalCampaign = campaignRepository.findById(dto.getCampaignId());
        if(optionalCampaign.isEmpty()){
            throw new NotFoundException();
        }
        var campaign = optionalCampaign.get();
        campaign.addHireRequest(hireRequest);
        campaignRepository.save(campaign);
    }

    @Override
    public List<HireRequestDto> getAll() {
        var user =  CurrentlyLoggedUserService.getCurrentlyLoggedUser();
        var requests= hireRequestRepository.findAllByInfluencerUsernameAndWaitingStatus(user.getUsername());
        var dtos =new ArrayList<HireRequestDto>();
        requests.forEach(hireRequest -> {
            var campaign = campaignRepository.findByHireRequestId(hireRequest.getId());
            dtos.add(new HireRequestDto(hireRequest.getInfluencerUsername(),hireRequest.getApprovalStatus(),
                    campaign.getId(), campaign.getAgentAccountUsername(), hireRequest.getId()));
        });
        return dtos;
    }

    @Override
    public void accept(Long id) {
        var optional = hireRequestRepository.findById(id);
        if(optional.isEmpty()){
            throw new NotFoundException();
        }
        var hireRequest = optional.get();
        hireRequest.setApprovalStatus(ApprovalStatus.APPROVED);
        hireRequestRepository.save(hireRequest);
    }

    @Override
    public void reject(Long id) {
        var optional = hireRequestRepository.findById(id);
        if(optional.isEmpty()){
            throw new NotFoundException();
        }
        var hireRequest = optional.get();
        hireRequest.setApprovalStatus(ApprovalStatus.APPROVED);
        hireRequestRepository.save(hireRequest);
    }
}
