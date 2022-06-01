package com.example.webapp.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Tournament {
    @Min(1)
    private Long id;

    @NotEmpty
    @NotNull
    private int year;

    @NotEmpty
    @NotNull
    private int numbOfParticipants;

    @NotEmpty
    @NotNull
    private int prizePool;

    @NotNull
    private String sponsors;

    public Tournament() {
    }

    public Tournament(@NotNull @NotEmpty int year,
                      @NotNull @NotEmpty int numbOfParticipants,
                      @NotNull @NotEmpty int prizePool,
                      @NotNull String sponsors) {
        this.year = year;
        this.numbOfParticipants = numbOfParticipants;
        this.prizePool = prizePool;
        this.sponsors = sponsors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNumbOfParticipants() {
        return numbOfParticipants;
    }

    public void setNumbOfParticipants(int numbOfParticipants) {
        this.numbOfParticipants = numbOfParticipants;
    }

    public int getPrizePool() {
        return prizePool;
    }

    public void setPrizePool(int prizePool) {
        this.prizePool = prizePool;
    }

    public String getSponsors() {
        return sponsors;
    }

    public void setSponsors(String sponsors) {
        this.sponsors = sponsors;
    }
}
