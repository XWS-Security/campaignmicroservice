package org.nistagram.campaignmicroservice.service;

import org.nistagram.campaignmicroservice.data.dto.GetAgentReportResponse;

import java.util.List;

public interface CampaignStatsService {
    List<GetAgentReportResponse> generate();
}
