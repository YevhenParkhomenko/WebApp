package com.example.webapp.entity;

import com.example.webapp.model.Participant;

import javax.persistence.*;

@Entity
public class ParticipantEntity extends Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Long getId() {
        return super.getId();
    }

    public ParticipantEntity() {
        super();
    }

    public ParticipantEntity(String name, int bYear, int rating, CountryEntity country, String lichessName) {
        super(name, bYear, rating, country, lichessName);
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Column(nullable = false)
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
    public int getbYear() {
        return super.getbYear();
    }

    @Override
    public void setbYear(int bYear) {
        super.setbYear(bYear);
    }

    @Column(nullable = false)
    @Override
    public int getRating() {
        return super.getRating();
    }

    @Override
    public void setRating(int rating) {
        super.setRating(rating);
    }

    @ManyToOne
    @Override
    public CountryEntity getCountry() {
        return super.getCountry();
    }

    @Override
    public void setCountry(CountryEntity country) {
        super.setCountry(country);
    }

    @Column(nullable = false, unique = true)
    @Override
    public String getLichessName() {
        return super.getLichessName();
    }

    @Override
    public void setLichessName(String lichessName) {
        super.setLichessName(lichessName);
    }
}
