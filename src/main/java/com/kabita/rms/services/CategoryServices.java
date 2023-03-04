package com.kabita.rms.services;

import java.util.List;

import com.kabita.rms.payload.CategoryDto;

public interface CategoryServices {
	CategoryDto addCategory(CategoryDto categoryDto);
	List<CategoryDto> getAllCategory();
	void deleteCategory(Integer categoryId);
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	CategoryDto getSingleCategory(Integer categoryId);
}
