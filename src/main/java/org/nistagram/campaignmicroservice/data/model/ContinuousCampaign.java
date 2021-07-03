package org.nistagram.campaignmicroservice.data.model;

import org.nistagram.campaignmicroservice.data.enums.Gender;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("CONTINUOUS_CAMPAIGN")
public class ContinuousCampaign extends Campaign {

    @Column(name = "exposure_start")
    private Date exposureStart;

    @Column(name = "exposure_end")
    private Date exposureEnd;

    @Column(name = "required_daily_displays")
    private int requiredDailyDisplays;

    @Column(name = "lastUpdate")
    private Date lastUpdate;

    public static final int TWENTY_FOUR_HOURS = 86400000;

    public ContinuousCampaign() {
    }

    public ContinuousCampaign(String agentAccountUsername, boolean deleted, Gender gender, Integer maxAge, Integer minAge, List<HireRequest> hireRequests, Advertisement advertisement, Date exposureStart, Date exposureEnd, int requiredDailyDisplays) {
        super(agentAccountUsername, deleted, gender, maxAge, minAge, hireRequests, advertisement);
        this.exposureStart = exposureStart;
        this.exposureEnd = exposureEnd;
        this.requiredDailyDisplays = requiredDailyDisplays;
        var date = new Date();
        date.setTime(date.getTime()- TWENTY_FOUR_HOURS -1);
        this.lastUpdate = date;
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

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
