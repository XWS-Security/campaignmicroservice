package org.nistagram.campaignmicroservice.data.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@DiscriminatorValue("ONE_TIME_CAMPAIGN")
public class OneTimeCampaign extends Campaign {

    @Column(name = "exposure_date")
    private Date exposureDate;

    public OneTimeCampaign() {
    }

    public Date getExposureDate() {
        return exposureDate;
    }

    public void setExposureDate(Date exposureDate) {
        this.exposureDate = exposureDate;
    }
}
