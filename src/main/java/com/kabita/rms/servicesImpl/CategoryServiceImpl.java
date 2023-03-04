package com.kabita.rms.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kabita.rms.entities.Category;
import com.kabita.rms.exception.ResourceNotFoundException;
import com.kabita.rms.payload.CategoryDto;
import com.kabita.rms.repository.CategoryRepository;
import com.kabita.rms.services.CategoryServices;

@Service
public class CategoryServiceImpl implements CategoryServices {
	@Autowired
	CategoryRepository categoryRepo;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
//		taking the user value and adding it to the database
		Category addCategory = this.categoryRepo.save(this.modelMapper.map(categoryDto, Category.class));

		return this.modelMapper.map(addCategory, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
//		list of category object obtained from database
		List<Category> categories = this.categoryRepo.findAll();
		return categories.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public void deleteCategory(Integer categoryId) {
//		retrieving category object and deleting it
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
//		retrieving category object
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		category.setCategoryName(categoryDto.getCategoryName());
//saving updated category object to database
		Category updatedCategory = this.categoryRepo.save(category);

		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto getSingleCategory(Integer categoryId) {
//		getting category object
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		return this.modelMapper.map(category, CategoryDto.class);
	}

}
