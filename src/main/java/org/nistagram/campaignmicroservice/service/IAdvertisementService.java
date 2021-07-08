package org.nistagram.campaignmicroservice.service;

import org.nistagram.campaignmicroservice.data.dto.AdvertisementDto;

import javax.net.ssl.SSLException;
import java.util.List;

public interface IAdvertisementService {
    List<AdvertisementDto> getUsersOneTimeAdvertisements(AdvertisementDto advertisementDto, String token) throws SSLException;

    void see(Long id);

    List<AdvertisementDto> getUsersContinuousAdvertisements(String token) throws SSLException;
}
