package org.nistagram.campaignmicroservice.controller;

import org.nistagram.campaignmicroservice.data.dto.AdvertisementDto;
import org.nistagram.campaignmicroservice.security.TokenUtils;
import org.nistagram.campaignmicroservice.service.IAdvertisementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/advertisement", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AdvertisementController {

    private final IAdvertisementService advertisementService;
    private final TokenUtils tokenUtils;

    public AdvertisementController(IAdvertisementService advertisementService, TokenUtils tokenUtils) {
        this.advertisementService = advertisementService;
        this.tokenUtils = tokenUtils;
    }

    @PostMapping(path = "/onetime")
    public ResponseEntity<List<AdvertisementDto>> getUsersOneTimePostAdvertisements(@RequestBody AdvertisementDto advertisementDto, HttpServletRequest request) {
        try {
            List<AdvertisementDto> dtos = advertisementService.getUsersOneTimeAdvertisements(advertisementDto, tokenUtils.getToken(request));
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/continuous")
    public ResponseEntity<List<AdvertisementDto>> getUsersContinuousPostAdvertisements(HttpServletRequest request) {
        try {
            List<AdvertisementDto> dtos = advertisementService.getUsersContinuousAdvertisements(tokenUtils.getToken(request));
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/onetime/see/{id}")
    public ResponseEntity<String> see(@PathVariable Long id) {
        try {
            advertisementService.see(id);
            return new ResponseEntity<>("Advertisement " + id + " seen.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/continuous/profile/{agentUsername}")
    public ResponseEntity<List<AdvertisementDto>> getContinuousPostAdvertisementsOfAgent(HttpServletRequest request, @PathVariable String agentUsername) {
        try {
            List<AdvertisementDto> dtos = advertisementService.getContinuousPostAdvertisementsOfAgent(agentUsername, tokenUtils.getToken(request));
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/onetime/profile")
    public ResponseEntity<List<AdvertisementDto>> getOneTimePostAdvertisementsOfAgent(@RequestBody AdvertisementDto advertisementDto, HttpServletRequest request) {
        try {
            List<AdvertisementDto> dtos = advertisementService.getOneTimePostAdvertisementsOfAgent(advertisementDto, tokenUtils.getToken(request));
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/continuous/influencer/{influencerUsername}")
    public ResponseEntity<List<AdvertisementDto>> getContinuousPostAdvertisementsOfInfluencer(HttpServletRequest request, @PathVariable String influencerUsername) {
        try {
            List<AdvertisementDto> dtos = advertisementService.getContinuousPostAdvertisementsOfInfluencer(influencerUsername, tokenUtils.getToken(request));
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/onetime/influencer")
    public ResponseEntity<List<AdvertisementDto>> getOneTimePostAdvertisementsOfInfluencerUsername(@RequestBody AdvertisementDto advertisementDto, HttpServletRequest request) {
        try {
            List<AdvertisementDto> dtos = advertisementService.getOneTimePostAdvertisementsOfInfluencer(advertisementDto, tokenUtils.getToken(request));
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
