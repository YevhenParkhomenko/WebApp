package com.example.webapp.service;

import com.example.webapp.entity.SponsorEntity;
import com.example.webapp.model.Sponsor;
import com.example.webapp.repository.ResultRepo;
import com.example.webapp.repository.SponsorRepo;
import com.example.webapp.repository.SponsorsTSTournamentsRepo;
import com.example.webapp.repository.TournamentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SponsorService {
    @Autowired
    private SponsorRepo sponsorRepo;

    @Autowired
    private SponsorsTSTournamentsRepo sponsorTSTournamentRepo;

    @Autowired
    private ResultRepo resultRepo;

    @Autowired
    private TournamentRepo tournamentRepo;

    @Autowired
    private SponsorsTSTournamentsRepo sponsorsTSTournamentsRepo;

    public SponsorRepo getSponsorRepo() {
        return sponsorRepo;
    }


    public String createSponsorCheck(Sponsor sponsor){
        sponsor.setName(sponsor.getName().trim());
        if (sponsor.getName().equals("")){
            return "Не залишайте поля порожніми!";
        }
        if (sponsorRepo.findByName(sponsor.getName()) != null) {
            return "Неможливо створити ще одного спонсора з такою назвою!";
        }
        return null;
    }

    public String editSponsorCheck(Sponsor sponsor){
        sponsor.setName(sponsor.getName().trim());
        if (sponsor.getName().equals("")){
            return "Не залишайте поле \"назва\" порожнім!";
        }

        sponsor.setSpecialStatus(sponsor.getSpecialStatus().trim());
        if (sponsor.getSpecialStatus().equals("")){
            return "Не залишайте поле \"спеціальний статус\" порожнім!";
        }

        SponsorEntity temp = sponsorRepo.findById(sponsor.getId()).get();
        if (sponsor.getName().equals(temp.getName())) {
            if (sponsor.getSpecialStatus().equals(temp.getSpecialStatus())) {
                return "Змініть хоч щось!";
            }

            return null;
        }

        if (sponsorRepo.findByName(sponsor.getName()) != null){
            return "Спонсор з такою назвою вже є!";
        }
        return null;
    }

    public SponsorsTSTournamentsRepo getSponsorsTSTournamentsRepo() {
        return sponsorTSTournamentRepo;
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
