package com.datajpa.relationship.persistence.repository;

import com.datajpa.relationship.persistence.entity.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
}
