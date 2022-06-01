package com.example.webapp.repository;

import com.example.webapp.entity.SponsorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SponsorRepo extends CrudRepository<SponsorEntity, Long> {
    SponsorEntity findByName(String name);
}
