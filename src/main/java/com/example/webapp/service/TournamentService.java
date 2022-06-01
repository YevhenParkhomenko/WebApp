package com.example.webapp.service;

import com.example.webapp.entity.SponsorEntity;
import com.example.webapp.entity.TournamentEntity;
import com.example.webapp.model.SponsorsTSTournament;
import com.example.webapp.model.Tournament;
import com.example.webapp.repository.ResultRepo;
import com.example.webapp.repository.SponsorsTSTournamentsRepo;
import com.example.webapp.repository.TournamentRepo;
import org.apache.catalina.util.ErrorPageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TournamentService {
    @Autowired
    private TournamentRepo tournamentRepo;

    @Autowired
    private SponsorsTSTournamentsRepo sponsorsTSTournament;

    @Autowired
    private ResultRepo resultRepo;

    public TournamentRepo getTournamentRepo() {
        return tournamentRepo;
    }

    public String createTournamentCheck(Tournament tournament) {
        if (tournamentRepo.findByYear(tournament.getYear()) != null) {
            return "Вже є запис про турнір з таким роком!";
        }
        return null;
    }

    public String editTournamentCheck(Tournament tournament) {
        TournamentEntity temp = tournamentRepo.findById(tournament.getId()).get();
        if (tournament.getYear() == temp.getYear()) {
            if (tournament.getPrizePool() == temp.getPrizePool()) {
                return "Змініть хоч щось!";
            }

            return null;
        }

        if (tournamentRepo.findByYear(tournament.getYear()) != null) {
            return "Вже є запис про турнір з таким роком!";
        }
        return null;
    }

    public SponsorsTSTournamentsRepo getSponsorsTSTournamentsRepo() {
        return sponsorsTSTournament;
    }

    public ResultRepo getResultRepo() {
        return resultRepo;
    }
}
