package com.example.webapp.entity;

import com.example.webapp.model.Country;

import javax.persistence.*;

@Entity
public class CountryEntity extends Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Long getId() {
        return super.getId();
    }

    public CountryEntity() {
        super();
    }

    public CountryEntity(String name) {
        super(name);
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
}
