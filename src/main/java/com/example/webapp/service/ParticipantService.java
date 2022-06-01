package com.example.webapp.service;

import com.example.webapp.entity.ParticipantEntity;
import com.example.webapp.entity.SponsorEntity;
import com.example.webapp.model.Participant;
import com.example.webapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipantService {
    @Autowired
    private ParticipantRepo participantRepo;

    @Autowired
    private CountryRepo countryRepo;

    @Autowired
    private ResultRepo resultRepo;

    @Autowired
    private TournamentRepo tournamentRepo;

    @Autowired
    private SponsorsTSTournamentsRepo sponsorsTSTournamentsRepo;

    public ParticipantRepo getParticipantRepo() {
        return participantRepo;
    }

    public CountryRepo getCountryRepo() {
        return countryRepo;
    }

    public String createParticipantCheck(Participant participant) {
        if (participant.getCountry() == null) {
            return "Неправильний ідентифікатор країни!";
        }
        if (participantRepo.findByLichessName(participant.getLichessName()) != null) {
            return "Неможливо створити ще одного учасника з таким нікнеймом!";
        }
        participant.setName(participant.getName().trim());
        if (participant.getName().equals("")){
            return "Не залишайте поле \"назва\" порожніми!";
        }
        return null;
    }

    public String editParticipantCheck(Participant participant) {
        if (participant.getCountry() == null) {
            return "Неправильний ідентифікатор країни!";
        }

        participant.setName(participant.getName().trim());
        if (participant.getName().equals("")){
            return "Не залишайте поле \"ім'я\" порожніми!";
        }

        ParticipantEntity temp = participantRepo.findById(participant.getId()).get();
        if (participant.getLichessName().equals(temp.getLichessName())) {
            if (participant.getName().equals(temp.getName())
                    && participant.getbYear() == temp.getbYear()
                    && participant.getRating() == temp.getRating()
                    && participant.getCountry().getId() == temp.getCountry().getId()) {
                return "Змініть хоч щось!";
            }
            return null;
        }

        if (participantRepo.findByLichessName(participant.getLichessName()) != null) {
            return "Неможливо створити ще одного учасника з таким нікнеймом!";
        }
        return null;
    }

    public ResultRepo getResultRepo() {
        return resultRepo;
    }

    public TournamentRepo getTournamentRepo() {
        return tournamentRepo;
    }

    public SponsorsTSTournamentsRepo getSponsorsTSTournamentRepo() {
        return sponsorsTSTournamentsRepo;
    }
}
