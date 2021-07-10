package org.nistagram.campaignmicroservice.service.impl;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.nistagram.campaignmicroservice.data.dto.FollowingStatusDto;
import org.nistagram.campaignmicroservice.data.dto.GetAgentReportResponse;
import org.nistagram.campaignmicroservice.data.dto.LikesDislikesCommentsDto;
import org.nistagram.campaignmicroservice.data.dto.UsernameNumbersDto;
import org.nistagram.campaignmicroservice.data.model.*;
import org.nistagram.campaignmicroservice.data.repository.AdvertisementClickRepository;
import org.nistagram.campaignmicroservice.data.repository.AdvertisementRepository;
import org.nistagram.campaignmicroservice.data.repository.AdvertisementViewRepository;
import org.nistagram.campaignmicroservice.data.repository.CampaignRepository;
import org.nistagram.campaignmicroservice.exceptions.NotFoundException;
import org.nistagram.campaignmicroservice.exceptions.UserDoesNotExistException;
import org.nistagram.campaignmicroservice.service.CampaignStatsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampaignStatsServiceImpl implements CampaignStatsService {
    private final CampaignRepository campaignRepository;
    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementClickRepository advertisementClickRepository;
    private final AdvertisementViewRepository advertisementViewRepository;

    @Value("${CONTENT}")
    private String contentService;

    public CampaignStatsServiceImpl(CampaignRepository campaignRepository, AdvertisementRepository advertisementRepository, AdvertisementClickRepository advertisementClickRepository, AdvertisementViewRepository advertisementViewRepository) {
        this.campaignRepository = campaignRepository;
        this.advertisementRepository = advertisementRepository;
        this.advertisementClickRepository = advertisementClickRepository;
        this.advertisementViewRepository = advertisementViewRepository;
    }

    @Override
    public List<GetAgentReportResponse> generate() {
        User user = CurrentlyLoggedUserService.getCurrentlyLoggedUser();
        if (user == null) throw new UserDoesNotExistException();
        List<Campaign> campaigns = campaignRepository.findAllByAgentAccountUsername(user.getUsername());
        return campaigns.stream().map(campaign -> {
            LikesDislikesCommentsDto likesDislikesComments = null;
            try {
                likesDislikesComments = getContentData(campaign);
            } catch (SSLException e) {
                e.printStackTrace();
            }
            var result = new GetAgentReportResponse(campaign.getId(), getContentId(campaign), likesDislikesComments.getLikes(), likesDislikesComments.getDislikes(), likesDislikesComments.getComments(), getClicks(campaign), getViews(campaign));
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

    private LikesDislikesCommentsDto getContentData(Campaign campaign) throws SSLException {
        var id = getContentId(campaign);
        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));

        WebClient client = WebClient.builder()
                .baseUrl(contentService)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        return client.get()
                .uri("/content/basic/" + id)
                .retrieve()
                .bodyToMono(LikesDislikesCommentsDto.class).block();
    }

    private List<UsernameNumbersDto> getClicks(Campaign campaign){
        var id = campaign.getId();
        var list = advertisementClickRepository.findAllByCampaign_Id(id);
        var dtos = new ArrayList<UsernameNumbersDto>();
        HashMap<String,Integer> clicks = new HashMap<>();
        list.forEach(advertisementClick -> {
            var username = advertisementClick.getContentOwnerUsername();
            if(clicks.containsKey(username)){
                var oldValue =clicks.get(username);
                clicks.put(username,oldValue+1);
            }
            else clicks.put(advertisementClick.getContentOwnerUsername(),0);
        });
        clicks.keySet().forEach(username -> dtos.add(new UsernameNumbersDto(username, clicks.get(username))));
        return null;
    }

    private List<UsernameNumbersDto> getViews(Campaign campaign){
        var id = campaign.getId();
        var list = advertisementViewRepository.findAllByCampaign_Id(id);
        var dtos = new ArrayList<UsernameNumbersDto>();
        HashMap<String,Integer> clicks = new HashMap<>();
        list.forEach(advertisementClick -> {
            var username = advertisementClick.getContentOwnerUsername();
            if(clicks.containsKey(username)){
                var oldValue =clicks.get(username);
                clicks.put(username,oldValue+1);
            }
            else clicks.put(advertisementClick.getContentOwnerUsername(),0);
        });
        clicks.keySet().forEach(username -> dtos.add(new UsernameNumbersDto(username, clicks.get(username))));
        return null;
    }


    private Long getContentId(Campaign campaign){
        var advertisementId = campaign.getAdvertisement().getContentId();
        var advertisementOpt = advertisementRepository.findById(advertisementId);
        if(advertisementOpt.isEmpty()){
            throw new NotFoundException();
        }
        return advertisementOpt.get().getContentId();
    }
}
