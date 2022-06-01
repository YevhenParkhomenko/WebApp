package com.example.webapp.repository;

import com.example.webapp.entity.CountryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepo extends CrudRepository<CountryEntity, Long>{

    CountryEntity findByName(String name);
}
