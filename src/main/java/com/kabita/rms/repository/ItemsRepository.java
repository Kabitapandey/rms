package com.kabita.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kabita.rms.entities.Category;
import com.kabita.rms.entities.Items;

public interface ItemsRepository extends JpaRepository<Items, Integer> {
//	getting products according to there categories
	List<Items> getByCategory(Category category);

}
