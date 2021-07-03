package org.nistagram.campaignmicroservice.data.model;

import org.nistagram.campaignmicroservice.data.enums.Gender;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("ONE_TIME_CAMPAIGN")
public class OneTimeCampaign extends Campaign {

    @Column(name = "exposure_date")
    private Date exposureDate;

    public OneTimeCampaign() {
    }

    public OneTimeCampaign(String agentAccountUsername, boolean deleted, Gender gender, Integer maxAge, Integer minAge, List<HireRequest> hireRequests, Advertisement advertisement, Date exposureDate) {
        super(agentAccountUsername, deleted, gender, maxAge, minAge, hireRequests, advertisement);
        this.exposureDate = exposureDate;
    }

    public Date getExposureDate() {
        return exposureDate;
    }

    public void setExposureDate(Date exposureDate) {
        this.exposureDate = exposureDate;
    }
}
