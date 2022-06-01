package com.example.webapp.entity;

import com.example.webapp.model.Tournament;

import javax.persistence.*;

@Entity
public class TournamentEntity extends Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Long getId() {
        return super.getId();
    }

    public TournamentEntity() {
        super();
    }

    public TournamentEntity(int year, int numbOfParticipants, int prizePool, String sponsors) {
        super(year, numbOfParticipants, prizePool, sponsors);
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Column(nullable = false)
    @Override
    public int getYear() {
        return super.getYear();
    }

    @Override
    public void setYear(int year) {
        super.setYear(year);
    }

    @Column(nullable = false)
    @Override
    public int getNumbOfParticipants() {
        return super.getNumbOfParticipants();
    }

    @Override
    public void setNumbOfParticipants(int numbOfParticipants) {
        super.setNumbOfParticipants(numbOfParticipants);
    }

    @Column(nullable = false)
    @Override
    public int getPrizePool() {
        return super.getPrizePool();
    }

    @Override
    public void setPrizePool(int prizePool) {
        super.setPrizePool(prizePool);
    }

    @Column(nullable = false)
    @Override
    public String getSponsors() {
        return super.getSponsors();
    }

    @Override
    public void setSponsors(String sponsors) {
        super.setSponsors(sponsors);
    }
}
