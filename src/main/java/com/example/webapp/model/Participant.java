package com.example.webapp.model;

import com.example.webapp.entity.CountryEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Participant {
    @Min(1)
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private int bYear;

    @NotNull
    @NotEmpty
    private int rating;

    @NotNull
    @NotEmpty
    private CountryEntity country;

    @NotNull
    @NotEmpty
    private String lichessName;

    public Participant() {
    }

    public Participant(@NotNull @NotEmpty String name,
                       @NotNull @NotEmpty int bYear,
                       @NotNull @NotEmpty int rating,
                       @NotNull @NotEmpty CountryEntity country,
                       @NotNull @NotEmpty String lichessName) {
        this.name = name;
        this.bYear = bYear;
        this.rating = rating;
        this.country = country;
        this.lichessName = lichessName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getbYear() {
        return bYear;
    }

    public void setbYear(int bYear) {
        this.bYear = bYear;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }

    public String getLichessName() {
        return lichessName;
    }

    public void setLichessName(String lichessName) {
        this.lichessName = lichessName;
    }
}
