package com.example.webapp.service;

import com.example.webapp.entity.ResultEntity;
import com.example.webapp.model.Result;
import com.example.webapp.repository.ParticipantRepo;
import com.example.webapp.repository.ResultRepo;
import com.example.webapp.repository.TournamentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultService {
    @Autowired
    private ResultRepo resultRepo;

    @Autowired
    private TournamentRepo tournamentRepo;

    @Autowired
    private ParticipantRepo participantRepo;

    public ResultRepo getResultRepo() {
        return resultRepo;
    }

    public TournamentRepo getTournamentRepo() {
        return tournamentRepo;
    }

    public ParticipantRepo getParticipantRepo() {
        return participantRepo;
    }

    public String createResultCheck(Result result) {
        if (result.getTournament() == null) {
            return "Неправильний ідентифікатор турніру!";
        }
        if (result.getParticipant() == null) {
            return "Неправильний ідентифікатор учасника!";
        }
        if (resultRepo.findByTournamentIdAndParticipantId(result.getTournament().getId(), result.getParticipant().getId()) != null){
            return "Цей учасник уже є в цьому турнірі";
        }
        return null;
    }

    public String editResultCheck(Result result){
        if (result.getTournament() == null) {
            return "Неправильний ідентифікатор турніру!";
        }
        if (result.getParticipant() == null) {
            return "Неправильний ідентифікатор учасника!";
        }
        ResultEntity temp = resultRepo.findByTournamentIdAndParticipantId(result.getTournament().getId(), result.getParticipant().getId());
        if ( temp != null &&
            temp.getId() != result.getId()){
            return "Цей учасник уже є в цьому турнірі";
        }
        if ( temp != null &&
                temp.getId() == result.getId() &&
            temp.getPlace() == result.getPlace() &&
        temp.getPoints() == result.getPoints() &&
        temp.getPerformance() == result.getPerformance()){
            return "Змініть хоч щось";
        }
        return null;
    }
    //TODO Performance validation
}
