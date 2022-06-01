package com.example.webapp.model;

import com.example.webapp.entity.SponsorEntity;
import com.example.webapp.entity.TournamentEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SponsorsTSTournament {

    @Min(1)
    private Long id;

    @NotNull
    @NotEmpty
    private SponsorEntity sponsor;

    @NotNull
    @NotEmpty
    private TournamentEntity tournament;

    public SponsorsTSTournament() {
    }

    public SponsorsTSTournament(@NotNull @NotEmpty SponsorEntity sponsor,
                                @NotNull @NotEmpty TournamentEntity tournament) {
        this.sponsor = sponsor;
        this.tournament = tournament;
    }

    public SponsorsTSTournament(@NotNull @NotEmpty TournamentEntity tournament) {
        this.tournament = tournament;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SponsorEntity getSponsor() {
        return sponsor;
    }

    public void setSponsor(SponsorEntity sponsor) {
        this.sponsor = sponsor;
    }

    public TournamentEntity getTournament() {
        return tournament;
    }

    public void setTournament(TournamentEntity tournament) {
        this.tournament = tournament;
    }
}
