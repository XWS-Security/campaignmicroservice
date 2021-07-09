package org.nistagram.campaignmicroservice.controller;

import org.nistagram.campaignmicroservice.data.dto.GetAgentReportResponse;
import org.nistagram.campaignmicroservice.service.CampaignStatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
public class AgentReportController {
    private final CampaignStatsService campaignStatsService;

    public AgentReportController(CampaignStatsService campaignStatsService) {
        this.campaignStatsService = campaignStatsService;
    }

    @GetMapping(path = "/")
    @PreAuthorize("hasAuthority('AGENT_ROLE')")
    public ResponseEntity<List<GetAgentReportResponse>> getCampaignStats() {
        try {
            var result = campaignStatsService.generate();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
