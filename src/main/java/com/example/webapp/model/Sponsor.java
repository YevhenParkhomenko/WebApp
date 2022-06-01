package com.example.webapp.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Sponsor {
    @Min(1)
    private Long id;

    @NotEmpty
    @NotNull
    private String name;

    @NotEmpty
    @NotNull
    private String specialStatus;

    public Sponsor() {
    }

    public Sponsor(String name, String specialStatus) {
        this.name = name;
        this.specialStatus = specialStatus;
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

    public String getSpecialStatus() {
        return specialStatus;
    }

    public void setSpecialStatus(String specialStatus) {
        this.specialStatus = specialStatus;
    }
}
