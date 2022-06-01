package com.example.webapp.entity;

import com.example.webapp.model.Sponsor;

import javax.persistence.*;

@Entity
public class SponsorEntity extends Sponsor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Long getId() {
        return super.getId();
    }

    public SponsorEntity() {
        super();
    }

    public SponsorEntity(String name, String specialStatus) {
        super(name, specialStatus);
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Column(nullable = false, unique = true)
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Column(nullable = false)
    @Override
    public String getSpecialStatus() {
        return super.getSpecialStatus();
    }

    @Override
    public void setSpecialStatus(String specialStatus) {
        super.setSpecialStatus(specialStatus);
    }
}
