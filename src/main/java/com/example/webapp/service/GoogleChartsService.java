package com.example.webapp.service;

import com.example.webapp.entity.CountryEntity;
import com.example.webapp.entity.CountryEntity;
import com.example.webapp.repository.ParticipantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.TreeMap;

@Controller
public class GoogleChartsService {

    @Autowired
    ParticipantRepo participantRepo;

    public Map<String, Integer> getParticipantCountryMap(Iterable<CountryEntity> countries) {
        Map<String, Integer> countriesMap = new TreeMap<>();
        for (CountryEntity country : countries) {
            countriesMap.put(country.getName(), participantRepo.countAllByCountryId(country.getId()));
        }
        return countriesMap;
    }
}
