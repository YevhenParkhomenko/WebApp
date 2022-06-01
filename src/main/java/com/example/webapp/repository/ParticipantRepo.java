package com.example.webapp.repository;

import com.example.webapp.entity.ParticipantEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepo extends CrudRepository<ParticipantEntity, Long> {
    Iterable<ParticipantEntity> findAll(Sort sort);

    List<ParticipantEntity> findAllByCountryId(Long id, Sort sort);

    Iterable<ParticipantEntity> findAllByCountryId(Long id);

    Iterable<ParticipantEntity> deleteAllByCountryId(Long id);

    ParticipantEntity findByLichessName(String lichessName);

    int countAllByCountryId(Long id);
}
