package org.nistagram.campaignmicroservice.service;

import org.nistagram.campaignmicroservice.data.dto.AdvertisementDto;

import javax.net.ssl.SSLException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IAdvertisementService {
    List<AdvertisementDto> getUsersOneTimeAdvertisements(AdvertisementDto advertisementDto, String token) throws SSLException;

    void see(Long id, String contentOwnerUsername);

    List<AdvertisementDto> getUsersContinuousAdvertisements(String token) throws SSLException;

    List<AdvertisementDto> getContinuousPostAdvertisementsOfAgent(String agentUsername, String token) throws SSLException;

    List<AdvertisementDto> getOneTimePostAdvertisementsOfAgent(AdvertisementDto advertisementDto, String token) throws SSLException;

    List<AdvertisementDto> getContinuousPostAdvertisementsOfInfluencer(String influencerUsername, String token) throws SSLException;

    List<AdvertisementDto> getOneTimePostAdvertisementsOfInfluencer(AdvertisementDto advertisementDto, String token) throws SSLException;

    List<AdvertisementDto> getContinuousStoryAdvertisementsOfAgent(String agentUsername, String token) throws SSLException;

    List<AdvertisementDto> getOneTimeStoryAdvertisementsOfAgent(AdvertisementDto advertisementDto, String token) throws SSLException;

    List<AdvertisementDto> getContinuousStoryAdvertisementsOfInfluencer(String influencerUsername, String token) throws SSLException;

    List<AdvertisementDto> getOneTimeStoryAdvertisementsOfInfluencer(AdvertisementDto advertisementDto, String token) throws SSLException;

    AdvertisementDto getAd(Long id);

    void click(Long id, String contentOwnerUsername);
}
