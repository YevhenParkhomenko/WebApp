package com.example.webapp.repository;

import com.example.webapp.entity.SponsorsTSTournamentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Min;

@Repository
public interface SponsorsTSTournamentsRepo extends CrudRepository<SponsorsTSTournamentEntity, Long> {
    SponsorsTSTournamentEntity findBySponsorIdAndTournamentId(Long sponsor_id, Long tournament_id);

    Iterable<SponsorsTSTournamentEntity> findAllByTournamentId(Long tournament_id);

    Iterable<SponsorsTSTournamentEntity> findAllBySponsorId(@Min(1) Long sponsor_id);

    Iterable<SponsorsTSTournamentEntity> deleteAllBySponsor_Id(Long id);

    Iterable<SponsorsTSTournamentEntity> deleteAllByTournament_Id(Long id);
}
