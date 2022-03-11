package com.datajpa.relationship.persistence.repository;

import com.datajpa.relationship.persistence.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
