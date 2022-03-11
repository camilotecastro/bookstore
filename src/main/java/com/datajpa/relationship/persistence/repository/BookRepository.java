package com.datajpa.relationship.persistence.repository;

import com.datajpa.relationship.persistence.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
}
