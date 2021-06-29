package org.nistagram.campaignmicroservice.exceptions;

public class CampaignUpdatedBefore24H extends RuntimeException{
    public CampaignUpdatedBefore24H(){
        super("Campaign has been updated in last 24 hours.");
    }
}
