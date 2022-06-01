package com.example.webapp.model;

import com.example.webapp.entity.ParticipantEntity;
import com.example.webapp.entity.TournamentEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Result {
    @Min(1)
    private Long id;

    @NotNull
    @NotEmpty
    private TournamentEntity tournament;

    @NotNull
    @NotEmpty
    private ParticipantEntity participant;

    @NotNull
    @NotEmpty
    private int place;

    @NotNull
    @NotEmpty
    private int points;

    @NotNull
    @NotEmpty
    private int performance;

    public Result() {
    }

    public Result(@NotNull @NotEmpty TournamentEntity tournament,
                  @NotNull @NotEmpty ParticipantEntity participant,
                  @NotNull @NotEmpty int place,
                  @NotNull @NotEmpty int points,
                  @NotNull @NotEmpty int performance) {
        this.tournament = tournament;
        this.participant = participant;
        this.place = place;
        this.points = points;
        this.performance = performance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TournamentEntity getTournament() {
        return tournament;
    }

    public void setTournament(TournamentEntity tournament) {
        this.tournament = tournament;
    }

    public ParticipantEntity getParticipant() {
        return participant;
    }

    public void setParticipant(ParticipantEntity participant) {
        this.participant = participant;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }
}
