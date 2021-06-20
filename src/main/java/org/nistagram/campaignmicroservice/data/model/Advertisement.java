package org.nistagram.campaignmicroservice.data.model;

import javax.persistence.*;

@Entity
@Table(name = "advertisement")
public class Advertisement {
    @Id
    @SequenceGenerator(name = "advertisement_sequence_generator", sequenceName = "advertisement_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "advertisement_sequence_generator")
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "content_id")
    private Long contentId;

    @Column(name = "link")
    private String link;

    public Advertisement() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
