package com.grepp.synapse4.app.model.user.entity;


import com.grepp.synapse4.app.model.user.code.Category;
import com.grepp.synapse4.infra.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter @ToString
public class Survey extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id")
    private Long id;
    private String companyLocation;
    private String purpose;
    private String companion;

    @Enumerated(EnumType.STRING)
    private Category favoriteCategory;
    private String preferredMood;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
