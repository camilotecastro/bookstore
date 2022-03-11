package com.datajpa.relationship.persistence.repository;

import com.datajpa.relationship.persistence.entity.Zipcode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZipCodeRepository extends CrudRepository<Zipcode, Long> {
}
