package org.nistagram.campaignmicroservice.data.model;

import org.nistagram.campaignmicroservice.data.enums.Gender;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "max_age")
    private Integer maxAge;

    @Column(name = "min_age")
    private Integer minAge;

    public Campaign() {
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "campaign_id")
    private List<HireRequest> hireRequests;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;

    public Campaign(Long agentAccountId, boolean deleted, Gender gender, Integer maxAge, Integer minAge, List<HireRequest> hireRequests, Advertisement advertisement) {
        this.agentAccountId = agentAccountId;
        this.deleted = deleted;
        this.gender = gender;
        this.maxAge = maxAge;
        this.minAge = minAge;
        this.hireRequests = hireRequests;
        this.advertisement = advertisement;
    }

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

    public List<HireRequest> getHireRequest() {
        return hireRequests;
    }

    public void setHireRequest(List<HireRequest> hireRequests) {
        this.hireRequests = hireRequests;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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

    public List<HireRequest> getHireRequests() {
        return hireRequests;
    }

    public void setHireRequests(List<HireRequest> hireRequests) {
        this.hireRequests = hireRequests;
    }

    public void addHireRequest(HireRequest hireRequest){
        if(hireRequests==null){
            hireRequests = new ArrayList<>();
        }
        hireRequests.add(hireRequest);
    }
}
