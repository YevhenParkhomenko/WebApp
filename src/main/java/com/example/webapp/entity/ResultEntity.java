package com.example.webapp.entity;

import com.example.webapp.model.Result;

import javax.persistence.*;

@Entity
public class ResultEntity extends Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Long getId() {
        return super.getId();
    }

    public ResultEntity() {
        super();
    }

    public ResultEntity(TournamentEntity tournamentEntity, ParticipantEntity participantEntity, int place, int points, int performance) {
        super(tournamentEntity, participantEntity, place, points, performance);
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
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

    @ManyToOne
    @Override
    public ParticipantEntity getParticipant() {
        return super.getParticipant();
    }

    @Override
    public void setParticipant(ParticipantEntity participant) {
        super.setParticipant(participant);
    }

    @Column(nullable = false)
    @Override
    public int getPlace() {
        return super.getPlace();
    }

    @Override
    public void setPlace(int place) {
        super.setPlace(place);
    }

    @Column(nullable = false)
    @Override
    public int getPoints() {
        return super.getPoints();
    }

    @Override
    public void setPoints(int points) {
        super.setPoints(points);
    }

    @Column(nullable = false)
    @Override
    public int getPerformance() {
        return super.getPerformance();
    }

    @Override
    public void setPerformance(int performance) {
        super.setPerformance(performance);
    }
}
