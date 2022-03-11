package com.datajpa.relationship.persistence.repository;

import com.datajpa.relationship.persistence.entity.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {
}
