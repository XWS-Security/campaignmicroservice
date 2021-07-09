package org.nistagram.campaignmicroservice.service.impl;

import org.nistagram.campaignmicroservice.data.dto.GetAgentReportResponse;
import org.nistagram.campaignmicroservice.data.model.Campaign;
import org.nistagram.campaignmicroservice.data.model.ContinuousCampaign;
import org.nistagram.campaignmicroservice.data.model.OneTimeCampaign;
import org.nistagram.campaignmicroservice.data.model.User;
import org.nistagram.campaignmicroservice.data.repository.CampaignRepository;
import org.nistagram.campaignmicroservice.exceptions.UserDoesNotExistException;
import org.nistagram.campaignmicroservice.service.CampaignStatsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampaignStatsServiceImpl implements CampaignStatsService {
    private final CampaignRepository campaignRepository;

    public CampaignStatsServiceImpl(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    @Override
    public List<GetAgentReportResponse> generate() {
        User user = CurrentlyLoggedUserService.getCurrentlyLoggedUser();
        if (user == null) throw new UserDoesNotExistException();
        List<Campaign> campaigns = campaignRepository.findAllByAgentAccountUsername(user.getUsername());
        return campaigns.stream().map(campaign -> {
            // TODO: get likes, dislikes and comments
            var result = new GetAgentReportResponse(campaign.getId(), 1, 2, 3);
            if (campaign instanceof ContinuousCampaign) {
                ContinuousCampaign c = (ContinuousCampaign) campaign;
                result.setDateStart(c.getExposureStart());
                result.setDateEnd(c.getExposureEnd());
            } else if (campaign instanceof OneTimeCampaign) {
                OneTimeCampaign c = (OneTimeCampaign) campaign;
                result.setDate(c.getExposureDate());
            }
            return result;
        }).collect(Collectors.toList());
    }
}
