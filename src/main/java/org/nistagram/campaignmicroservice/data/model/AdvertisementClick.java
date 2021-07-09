package org.nistagram.campaignmicroservice.data.model;

import javax.persistence.*;

@Entity
@Table(name = "advertisement_click")
public class AdvertisementClick {

    @Id
    @SequenceGenerator(name = "click_sequence_generator", sequenceName = "click_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "click_sequence_generator")
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "username")
    String username;

    @Column(name = "content_owner_username")
    String contentOwnerUsername;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "campaign_id")
    Campaign campaign;

    public AdvertisementClick() {
    }

    public AdvertisementClick(Long id, String username, Campaign campaign) {
        this.id = id;
        this.username = username;
        this.campaign = campaign;
    }

    public AdvertisementClick(String username, Campaign campaign, String contentOwnerUsername) {
        this.id = id;
        this.username = username;
        this.campaign = campaign;
        this.contentOwnerUsername = contentOwnerUsername;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }
}
