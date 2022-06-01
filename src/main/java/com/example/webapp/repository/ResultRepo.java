package com.example.webapp.repository;

import com.example.webapp.entity.ResultEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepo extends CrudRepository<ResultEntity, Long> {
    int countAllByTournamentId(Long id);

    Iterable<ResultEntity> findAll(Sort sort);

    ResultEntity findByTournamentIdAndParticipantId(Long tournament_id, Long participant_id);

    List<ResultEntity> findAllByParticipantId(Long id);

    List<ResultEntity> findAllByTournamentId(Long id, Sort sort);

    Iterable<ResultEntity> deleteAllByTournament_Id(Long id);

    Iterable<ResultEntity> deleteAllByParticipant_Id(Long id);
}
