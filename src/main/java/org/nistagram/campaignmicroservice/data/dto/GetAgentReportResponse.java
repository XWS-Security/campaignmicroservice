package org.nistagram.campaignmicroservice.data.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class GetAgentReportResponse implements Serializable {
    private long campaignId;
    private long contentId;
    private int likes;
    private int dislikes;
    private int comments;
    private List<UsernameNumbersDto> clicks;
    private List<UsernameNumbersDto> views;
    private Date dateStart;
    private Date dateEnd;
    private Date date;

    public GetAgentReportResponse() {
    }

    public GetAgentReportResponse(long campaignId, long contentId, int likes, int dislikes, int comments, List<UsernameNumbersDto> clicks, List<UsernameNumbersDto> views) {
        this.campaignId = campaignId;
        this.contentId = contentId;
        this.likes = likes;
        this.dislikes = dislikes;
        this.comments = comments;
        this.clicks = clicks;
        this.views = views;
    }

    public long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(long campaignId) {
        this.campaignId = campaignId;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<UsernameNumbersDto> getClicks() {
        return clicks;
    }

    public void setClicks(List<UsernameNumbersDto> clicks) {
        this.clicks = clicks;
    }

    public List<UsernameNumbersDto> getViews() {
        return views;
    }

    public void setViews(List<UsernameNumbersDto> views) {
        this.views = views;
    }

    public long getContentId() {
        return contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }
}
