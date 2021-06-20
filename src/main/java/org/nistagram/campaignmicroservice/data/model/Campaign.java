package org.nistagram.campaignmicroservice.data.model;

import javax.persistence.*;

@Entity
@Table(name = "campaign")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "campaign_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Campaign {
    @Id
    @SequenceGenerator(name = "campaign_sequence_generator", sequenceName = "campaign_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campaign_sequence_generator")
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "agent_account_id")
    private Long agentAccountId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "hire_request_id")
    private HireRequest hireRequest;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAgentAccountId() {
        return agentAccountId;
    }

    public void setAgentAccountId(Long agentAccountId) {
        this.agentAccountId = agentAccountId;
    }

    public HireRequest getHireRequest() {
        return hireRequest;
    }

    public void setHireRequest(HireRequest hireRequest) {
        this.hireRequest = hireRequest;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }
}
