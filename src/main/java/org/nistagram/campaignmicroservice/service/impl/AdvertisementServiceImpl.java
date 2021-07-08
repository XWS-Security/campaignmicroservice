package org.nistagram.campaignmicroservice.service.impl;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.nistagram.campaignmicroservice.data.dto.AdvertisementDto;
import org.nistagram.campaignmicroservice.data.dto.BasicUserInfoDto;
import org.nistagram.campaignmicroservice.data.dto.FollowingStatusDto;
import org.nistagram.campaignmicroservice.data.enums.Gender;
import org.nistagram.campaignmicroservice.data.model.Advertisement;
import org.nistagram.campaignmicroservice.data.model.AdvertisementView;
import org.nistagram.campaignmicroservice.data.model.Campaign;
import org.nistagram.campaignmicroservice.data.repository.AdvertisementViewRepository;
import org.nistagram.campaignmicroservice.data.repository.CampaignRepository;
import org.nistagram.campaignmicroservice.data.repository.ContinuousCampaignRepository;
import org.nistagram.campaignmicroservice.data.repository.OneTimeCampaignRepository;
import org.nistagram.campaignmicroservice.exceptions.NotFoundException;
import org.nistagram.campaignmicroservice.service.IAdvertisementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertisementServiceImpl implements IAdvertisementService {

    private final OneTimeCampaignRepository oneTimeCampaignRepository;
    private final AdvertisementViewRepository advertisementViewRepository;
    private final CampaignRepository campaignRepository;
    private final ContinuousCampaignRepository continuousCampaignRepository;

    @Value("${ACCOUNT}")
    private String accountService;
    @Value("${FOLLOWER}")
    private String followerService;

    public AdvertisementServiceImpl(OneTimeCampaignRepository oneTimeCampaignRepository, AdvertisementViewRepository advertisementViewRepository, CampaignRepository campaignRepository, ContinuousCampaignRepository continuousCampaignRepository) {
        this.oneTimeCampaignRepository = oneTimeCampaignRepository;
        this.advertisementViewRepository = advertisementViewRepository;
        this.campaignRepository = campaignRepository;
        this.continuousCampaignRepository = continuousCampaignRepository;
    }

    @Override
    public List<AdvertisementDto> getUsersOneTimeAdvertisements(AdvertisementDto advertisementDto, String token) throws SSLException {

        var user = CurrentlyLoggedUserService.getCurrentlyLoggedUser();
        var username = user.getUsername();
        var userInfo = getUserInfo(username, token);
        var currentMoment = Calendar.getInstance();
        currentMoment.setTime(advertisementDto.getCurrentMoment());
        var allAgeRestrictedCampaigns = oneTimeCampaignRepository.
                findAllByDeletedFalseAndMinAgeLessThanEqualAndMaxAgeGreaterThanEqual(userInfo.getAge(), userInfo.getAge());
        var dtos = new ArrayList<AdvertisementDto>();
        allAgeRestrictedCampaigns.forEach(oneTimeCampaign -> {
            var views = advertisementViewRepository.findAllByUsernameAndCampaignsId(username, oneTimeCampaign.getId());
            var exposureDate = Calendar.getInstance();
            exposureDate.setTime(oneTimeCampaign.getExposureDate());
            var advertisement = oneTimeCampaign.getAdvertisement();
            try {
                var followingStatus = getFollowingStatus(oneTimeCampaign.getAgentAccountUsername(), token);
                if (followingStatus.getFollowing().equals("FOLLOWING") && !followingStatus.isMuted() && advertisement.isPost() && views.size() == 0 && isEqualDateInMinutesPrecision(currentMoment, exposureDate) && areGenderEqual(userInfo, oneTimeCampaign))
                    dtos.add(new AdvertisementDto(advertisementDto.getCurrentMoment(), advertisement.getContentId(), advertisement.getLink(), oneTimeCampaign.getAgentAccountUsername(), oneTimeCampaign.getId()));
            }catch (SSLException e){
                e.printStackTrace();
            }
        });
        return dtos;
    }


    @Override
    public void see(Long id) {
        var user = CurrentlyLoggedUserService.getCurrentlyLoggedUser();
        var campaignOptional = campaignRepository.findById(id);
        if(campaignOptional.isEmpty()){
            throw new NotFoundException(id);
        }
        var campaign = campaignOptional.get();
        assert user != null;
        advertisementViewRepository.save(new AdvertisementView(user.getUsername(), campaign));
    }

    @Override
    public List<AdvertisementDto> getUsersContinuousAdvertisements(String token) throws SSLException {
        var user = CurrentlyLoggedUserService.getCurrentlyLoggedUser();
        assert user != null;
        var username = user.getUsername();
        var userInfo = getUserInfo(username, token);
        var ageRestrictedCampaigns = continuousCampaignRepository.findAllByDeletedFalseAndMinAgeLessThanEqualAndMaxAgeGreaterThanEqual(userInfo.getAge(), userInfo.getAge());
        var dtos = new ArrayList<AdvertisementDto>();
        ageRestrictedCampaigns.forEach(continuousCampaign -> {
            var advertisement = continuousCampaign.getAdvertisement();
            try {
                var followingStatus = getFollowingStatus(continuousCampaign.getAgentAccountUsername(), token);
                if (followingStatus.getFollowing().equals("FOLLOWING") && !followingStatus.isMuted() && advertisement.isPost() && betweenDates(continuousCampaign.getExposureStart(), continuousCampaign.getExposureEnd()) && areGenderEqual(userInfo, continuousCampaign))
                    dtos.add(new AdvertisementDto(null, advertisement.getContentId(), advertisement.getLink(), continuousCampaign.getAgentAccountUsername(), continuousCampaign.getId()));
            } catch (SSLException e) {
                e.printStackTrace();
            }
        });
        return dtos;
    }

    @Override
    public List<AdvertisementDto> getContinuousPostAdvertisementsOfAgent(String agentUsername, String token) throws SSLException {
        var dtos = getUsersContinuousAdvertisements(token);
        dtos = dtos.stream().filter(advertisementDto -> advertisementDto.getAgentAccountUsername().equals(agentUsername)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public List<AdvertisementDto> getOneTimePostAdvertisementsOfAgent(AdvertisementDto advertisementDto, String token) throws SSLException {
        var dtos = getUsersOneTimeAdvertisements(advertisementDto, token);
        dtos = dtos.stream().filter(dto -> dto.getAgentAccountUsername().equals(advertisementDto.getAgentAccountUsername())).collect(Collectors.toList());
        return dtos;
    }

    private FollowingStatusDto getFollowingStatus(String username, String token) throws SSLException {
        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));

        WebClient client = WebClient.builder()
                .baseUrl(followerService)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        return client.get()
                .uri("/interactions/" + username)
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(FollowingStatusDto.class).block();
    }

    private boolean betweenDates(Date date1, Date date2){
        var cal = Calendar.getInstance();
        var cal1 = Calendar.getInstance();
        var cal2 = Calendar.getInstance();

        cal.setTime(new Date());
        cal1.setTime(date1);
        cal2.setTime(date2);

        cal.set(Calendar.HOUR_OF_DAY,0);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.HOUR_OF_DAY,0);

        cal.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.MINUTE,0);

        cal.set(Calendar.SECOND,0);
        cal1.set(Calendar.SECOND,0);
        cal2.set(Calendar.SECOND,0);


        cal.set(Calendar.MILLISECOND,0);
        cal1.set(Calendar.MILLISECOND,0);
        cal2.set(Calendar.MILLISECOND,0);

        date1 = cal1.getTime();
        date2 = cal2.getTime();
        var date = cal.getTime();
        boolean after=false;
        boolean before=false;
        if(date.compareTo(date1)>=0){
            after = true;
        }
        if(date.compareTo(date2)<=0){
            before = true;
        }
        System.out.println(after + " " +before);
        return before && after;
    }

    private boolean areGenderEqual(BasicUserInfoDto userInfo, Campaign campaign) {
        return campaign.getGender().equals(Gender.Both) || campaign.getGender().equals(userInfo.getGender());
    }

    private BasicUserInfoDto getUserInfo(String username, String token) throws SSLException {
        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));

        WebClient client = WebClient.builder()
                .baseUrl(accountService)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        return client.get()
                .uri("/profile/getBasicUserInfo/" + username)
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(BasicUserInfoDto.class).block();
    }

    private boolean isEqualDateInMinutesPrecision(Calendar date1, Calendar date2) {


        boolean year = date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR);
        boolean month = date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH);
        boolean day = date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH);
        boolean hour = date1.get(Calendar.HOUR_OF_DAY) == date2.get(Calendar.HOUR_OF_DAY);
        boolean minute = date1.get(Calendar.MINUTE) == date2.get(Calendar.MINUTE);
        return year && month && day && hour && minute;
    }
}
