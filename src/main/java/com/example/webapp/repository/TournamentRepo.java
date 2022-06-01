package com.example.webapp.repository;

import com.example.webapp.entity.ResultEntity;
import com.example.webapp.entity.TournamentEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepo extends CrudRepository<TournamentEntity, Long> {
    Iterable<TournamentEntity> findAll(Sort sort);

    TournamentEntity findByYear(int year);
}
