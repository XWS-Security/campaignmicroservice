package org.nistagram.campaignmicroservice.data.model;

import javax.persistence.*;

@Entity
@Table(name = "advertisement_view")
public class AdvertisementView {

    @Id
    @SequenceGenerator(name = "view_sequence_generator", sequenceName = "view_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "view_sequence_generator")
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "username")
    String username;

    @Column(name = "content_owner_username")
    String contentOwnerUsername;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "campaign_id")
    Campaign campaign;

    public AdvertisementView() {
    }

    public AdvertisementView(Long id, String username, Campaign campaign) {
        this.id = id;
        this.username = username;
        this.campaign = campaign;
    }

    public AdvertisementView(String username, Campaign campaign, String contentOwnerUsername) {
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

    public String getContentOwnerUsername() {
        return contentOwnerUsername;
    }

    public void setContentOwnerUsername(String contentOwnerUsername) {
        this.contentOwnerUsername = contentOwnerUsername;
    }
}
