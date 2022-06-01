package com.example.webapp.service;

import com.example.webapp.entity.CountryEntity;
import com.example.webapp.model.Country;
import com.example.webapp.repository.CountryRepo;
import com.example.webapp.repository.ParticipantRepo;
import com.example.webapp.repository.ResultRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

    @Autowired
    CountryRepo countryRepo;

    @Autowired
    private ParticipantRepo participantRepo;

    @Autowired
    private ResultRepo resultRepo;

    public CountryRepo getCountryRepo() {
        return countryRepo;
    }

    public String createCountryCheck(Country country){
        country.setName(country.getName().trim());
        if (country.getName().equals("")){
            return "Не залишайте поля порожніми!";
        }
        if (countryRepo.findByName(country.getName()) != null) {
            return "Неможливо створити ще одну країну з такою назвою!";
        }
        return null;
    }

    public String editCountryCheck(Country country){
        country.setName(country.getName().trim());
        if (country.getName().equals("")){
            return "Не залишайте поля порожніми!";
        }

        CountryEntity temp = countryRepo.findById(country.getId()).get();
        if (country.getName().equals(temp.getName())) {
            return "Назва збігається зі старою!";
        }

        if (countryRepo.findByName(country.getName()) != null){
            return "Країна з такою назвою вже є!";
        }
        return null;
    }

    public ParticipantRepo getParticipantRepo() {
        return participantRepo;
    }

    public ResultRepo getResultRepo() {
        return resultRepo;
    }
}
