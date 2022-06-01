package com.example.webapp.service;

import com.example.webapp.model.SponsorsTSTournament;
import com.example.webapp.repository.SponsorsTSTournamentsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SponsorsTSTournamentService {
    @Autowired
    private SponsorsTSTournamentsRepo sponsorsTSTournamentRepo;

    public SponsorsTSTournamentsRepo getSponsorsTSTournamentRepo() {
        return sponsorsTSTournamentRepo;
    }

    public String addSponsorCheck(SponsorsTSTournament sponsorsTSTournament){
        if (sponsorsTSTournament.getTournament() == null) {
            return "Неправильний ідентифікатор турніру.";
        }
        if (sponsorsTSTournament.getSponsor() == null) {
            return "Неправильний ідентифікатор спонсора.";
        }
        if (sponsorsTSTournamentRepo.findBySponsorIdAndTournamentId(sponsorsTSTournament.getSponsor().getId(),sponsorsTSTournament.getTournament().getId()) != null){
            return "Цей спонсор уже проспонсував цей турнір.";
        }
        return null;
    }
}
