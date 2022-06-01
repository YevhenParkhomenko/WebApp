package com.example.webapp.entity;

import com.example.webapp.model.SponsorsTSTournament;

import javax.persistence.*;

@Entity
public class SponsorsTSTournamentEntity extends SponsorsTSTournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Long getId() {
        return super.getId();
    }

    public SponsorsTSTournamentEntity() {
        super();
    }

    public SponsorsTSTournamentEntity(SponsorEntity sponsor, TournamentEntity tournament) {
        super(sponsor, tournament);
    }

    public SponsorsTSTournamentEntity(TournamentEntity tournament) {
        super(tournament);
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @ManyToOne
    @Override
    public SponsorEntity getSponsor() {
        return super.getSponsor();
    }

    @Override
    public void setSponsor(SponsorEntity sponsor) {
        super.setSponsor(sponsor);
    }

    @ManyToOne
    @Override
    public TournamentEntity getTournament() {
        return super.getTournament();
    }

    @Override
    public void setTournament(TournamentEntity tournament) {
        super.setTournament(tournament);
    }
}
