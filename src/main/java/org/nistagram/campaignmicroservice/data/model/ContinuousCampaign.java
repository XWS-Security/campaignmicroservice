package org.nistagram.campaignmicroservice.data.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("CONTINUOUS_CAMPAIGN")
public class ContinuousCampaign extends Campaign{

    @Column(name = "exposure_start")
    private Date exposureStart;

    @Column(name = "exposure_end")
    private Date exposureEnd;

    @Column(name = "required_daily_displays")
    private int requiredDailyDisplays;

    public ContinuousCampaign() {
    }

    public Date getExposureStart() {
        return exposureStart;
    }

    public void setExposureStart(Date exposureStart) {
        this.exposureStart = exposureStart;
    }

    public Date getExposureEnd() {
        return exposureEnd;
    }

    public void setExposureEnd(Date exposureEnd) {
        this.exposureEnd = exposureEnd;
    }

    public int getRequiredDailyDisplays() {
        return requiredDailyDisplays;
    }

    public void setRequiredDailyDisplays(int requiredDailyDisplays) {
        this.requiredDailyDisplays = requiredDailyDisplays;
    }
}
