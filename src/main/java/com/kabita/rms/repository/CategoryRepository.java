package com.kabita.rms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kabita.rms.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
