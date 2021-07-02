package org.nistagram.campaignmicroservice.controller;

import org.nistagram.campaignmicroservice.data.dto.CampaignDto;
import org.nistagram.campaignmicroservice.exceptions.CampaignUpdatedBefore24H;
import org.nistagram.campaignmicroservice.service.ICampaignService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/campaign", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class CampaignController {
    private final ICampaignService campaignService;

    public CampaignController(ICampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping(path = "/")
    public ResponseEntity<String> create(@RequestBody CampaignDto campaignDto) {
        try {
            campaignService.create(campaignDto);
            return new ResponseEntity<>("Campaign created successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Campaign couldn't be created!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/update")
    public ResponseEntity<String> update(@RequestBody CampaignDto campaignDto) {
        try {
            campaignService.update(campaignDto);
            return new ResponseEntity<>("Campaign updated successfully!", HttpStatus.OK);
        }catch (CampaignUpdatedBefore24H e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Campaign couldn't be updated!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/continuous")
    public ResponseEntity<List<CampaignDto>> getAllContinuous() {
        try {
            List<CampaignDto> dtos = campaignService.getAllContinuous();
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<CampaignDto>> getAll() {
        try {
            List<CampaignDto> dtos = campaignService.getAll();
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            campaignService.delete(id);
            return new ResponseEntity<>("Campaign deleted successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Campaign couldn't be deleted!", HttpStatus.BAD_REQUEST);
        }
    }
}
