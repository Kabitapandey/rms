package com.kabita.rms.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int categoryId;
	private String categoryName;
	
	@OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
	private List<Items> products;
}
