package org.nistagram.campaignmicroservice.service.impl;

import org.nistagram.campaignmicroservice.data.dto.CampaignDto;
import org.nistagram.campaignmicroservice.data.model.*;
import org.nistagram.campaignmicroservice.data.repository.AdvertisementRepository;
import org.nistagram.campaignmicroservice.data.repository.CampaignRepository;
import org.nistagram.campaignmicroservice.data.repository.ContinuousCampaignRepository;
import org.nistagram.campaignmicroservice.exceptions.CampaignUpdatedBefore24H;
import org.nistagram.campaignmicroservice.exceptions.NotFoundException;
import org.nistagram.campaignmicroservice.service.ICampaignService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CampaignServiceImpl implements ICampaignService {

    private final CampaignRepository campaignRepository;
    private final ContinuousCampaignRepository continuousCampaignRepository;
    private final AdvertisementRepository advertisementRepository;

    public CampaignServiceImpl(CampaignRepository campaignRepository, ContinuousCampaignRepository continuousCampaignRepository, AdvertisementRepository advertisementRepository) {
        this.campaignRepository = campaignRepository;
        this.continuousCampaignRepository = continuousCampaignRepository;
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    public void create(CampaignDto dto) {
        var advertisement = new Advertisement(dto.getContentId(), dto.getLink(), dto.isPost()) ;
        var agent =  CurrentlyLoggedUserService.getCurrentlyLoggedUser();
        assert agent != null;
        if(dto.isOneTime()){
            var exposureDateCalendar = Calendar.getInstance();
            final int javascriptHourDifference = 2;
            exposureDateCalendar.setTime(dto.getExposureDate());
            exposureDateCalendar.set(Calendar.HOUR_OF_DAY, exposureDateCalendar.get(Calendar.HOUR_OF_DAY) - javascriptHourDifference);
            var oneTimeCampaign = new OneTimeCampaign(agent.getUsername(),false,dto.getGender(), dto.getMaxAge(),dto.getMinAge()
                    ,new ArrayList<>(), advertisement, exposureDateCalendar.getTime());
            oneTimeCampaign.setAgentAccountUsername(agent.getUsername());
            advertisementRepository.save(advertisement);
            campaignRepository.save(oneTimeCampaign);
        }
        else{
            var continuousCampaign = new ContinuousCampaign(agent.getUsername(),false,dto.getGender(), dto.getMaxAge(),dto.getMinAge()
                    ,new ArrayList<>(), advertisement, dto.getExposureStart(), dto.getExposureEnd(), dto.getRequiredDailyDisplays());
            continuousCampaign.setAgentAccountUsername(agent.getUsername());
            advertisementRepository.save(advertisement);
            campaignRepository.save(continuousCampaign);
        }
    }

    @Override
    public void update(CampaignDto dto) {
        var campaignOptional = continuousCampaignRepository.findById(dto.getId());
        if(campaignOptional.isEmpty()){
            throw new NotFoundException();
        }
        var campaign = campaignOptional.get();
        var date = new Date();
        date.setTime(date.getTime()-ContinuousCampaign.TWENTY_FOUR_HOURS);
        if(!campaign.getLastUpdate().toInstant().isBefore(date.toInstant())){
            throw new CampaignUpdatedBefore24H();
        }
        campaign.setLastUpdate(new Date());
        campaign.setMaxAge(dto.getMaxAge());
        campaign.setMinAge(dto.getMinAge());
        campaign.setGender(dto.getGender());
        var advertisement = campaign.getAdvertisement();
        advertisement.setLink(dto.getLink());
        campaign.setAdvertisement(advertisement);
        advertisementRepository.save(advertisement);
        campaignRepository.save(campaign);
    }

    @Override
    public void delete(Long id) {
        var campaignOptional = campaignRepository.findById(id);
        if(campaignOptional.isEmpty()){
            throw new NotFoundException();
        }
        var campaign = campaignOptional.get();
        campaign.setDeleted(true);
        campaignRepository.save(campaign);
    }

    @Override
    public List<CampaignDto> getAllContinuous() {
        var user = CurrentlyLoggedUserService.getCurrentlyLoggedUser();
        var campaigns = continuousCampaignRepository.findAllByAgentAccountUsername(user.getUsername());
        List<CampaignDto> dtos = new ArrayList<>();

        campaigns.forEach(campaign -> {
            if(!campaign.isDeleted()){
                var advert = campaign.getAdvertisement();
                var dto = new CampaignDto( campaign.getId(), campaign.getMaxAge(), campaign.getMinAge(), campaign.getGender(), advert.getContentId(), advert.getLink());
                dto.setOneTime(false);
                dto.setExposureEnd(campaign.getExposureEnd());
                dto.setExposureStart(campaign.getExposureStart());
                dto.setRequiredDailyDisplays(campaign.getRequiredDailyDisplays());
                dtos.add(dto);
            }
        });
        return dtos;
    }

    @Override
    public List<CampaignDto> getAll() {
        var user = CurrentlyLoggedUserService.getCurrentlyLoggedUser();
        var campaigns = campaignRepository.findAllByAgentAccountUsername(user.getUsername());
        List<CampaignDto> dtos = new ArrayList<>();

        campaigns.forEach(campaign -> {
            if(!campaign.isDeleted()){
                var advert = campaign.getAdvertisement();
                var dto = new CampaignDto( campaign.getId(), campaign.getMaxAge(), campaign.getMinAge(), campaign.getGender(), advert.getContentId(), advert.getLink());

                if(campaign instanceof ContinuousCampaign){
                    dto.setExposureEnd(((ContinuousCampaign) campaign).getExposureEnd());
                    dto.setExposureStart(((ContinuousCampaign) campaign).getExposureStart());
                    dto.setRequiredDailyDisplays(((ContinuousCampaign) campaign).getRequiredDailyDisplays());
                    dto.setOneTime(false);
                }
                else {
                    dto.setOneTime(true);
                    dto.setExposureDate(((OneTimeCampaign) campaign).getExposureDate());
                }
                dtos.add(dto);
            }
        });
        return dtos;
    }
}
