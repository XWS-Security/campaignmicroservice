package org.nistagram.campaignmicroservice.data.dto;

import org.nistagram.campaignmicroservice.data.enums.Gender;

import java.io.Serializable;
import java.util.Date;

public class CampaignDto implements Serializable {

    private Long id;
    private Integer maxAge;
    private Integer minAge;
    private Gender gender;
    private Long contentId;
    private String link;
    private Date exposureStart;
    private Date exposureEnd;
    private int requiredDailyDisplays;
    private Date exposureDate;
    private boolean oneTime;

    public CampaignDto() {
    }

    public CampaignDto(Long id, Integer maxAge, Integer minAge, Gender gender, Long contentId, String link, Date exposureStart, Date exposureEnd, int requiredDailyDisplays, Date exposureDate, boolean oneTime) {
        this.id = id;
        this.maxAge = maxAge;
        this.minAge = minAge;
        this.gender = gender;
        this.contentId = contentId;
        this.link = link;
        this.exposureStart = exposureStart;
        this.exposureEnd = exposureEnd;
        this.requiredDailyDisplays = requiredDailyDisplays;
        this.exposureDate = exposureDate;
        this.oneTime = oneTime;
    }

    public CampaignDto(Long id, Integer maxAge, Integer minAge, Gender gender, Long contentId, String link) {
        this.id = id;
        this.maxAge = maxAge;
        this.minAge = minAge;
        this.gender = gender;
        this.contentId = contentId;
        this.link = link;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public Date getExposureDate() {
        return exposureDate;
    }

    public void setExposureDate(Date exposureDate) {
        this.exposureDate = exposureDate;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public void setOneTime(boolean oneTime) {
        this.oneTime = oneTime;
    }
}
