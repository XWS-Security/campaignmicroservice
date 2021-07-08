package org.nistagram.campaignmicroservice.data.model;

import javax.persistence.*;

@Entity
@Table(name = "advertisement_view")
public class AdvertisementView {

    @Id
    @SequenceGenerator(name = "campaign_sequence_generator", sequenceName = "campaign_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campaign_sequence_generator")
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "username")
    String username;

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

    public AdvertisementView(String username, Campaign campaign) {
        this.id = id;
        this.username = username;
        this.campaign = campaign;
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
