package org.nistagram.campaignmicroservice.controller;

import org.nistagram.campaignmicroservice.data.dto.CampaignDto;
import org.nistagram.campaignmicroservice.data.dto.HireRequestDto;
import org.nistagram.campaignmicroservice.data.model.HireRequest;
import org.nistagram.campaignmicroservice.service.IHireRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hireRequest", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class HireRequestController {

    private final IHireRequestService hireRequestService;

    public HireRequestController(IHireRequestService hireRequestService) {
        this.hireRequestService = hireRequestService;
    }

    @PostMapping(path = "/")
    public ResponseEntity<String> create(@RequestBody HireRequestDto dto) {
        try {
            hireRequestService.create(dto);
            return new ResponseEntity<>("Campaign created successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Campaign couldn't be created!", HttpStatus.BAD_REQUEST);
        }
    }
}
