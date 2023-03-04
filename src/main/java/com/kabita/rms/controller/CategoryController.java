package com.kabita.rms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kabita.rms.payload.ApiResponse;
import com.kabita.rms.payload.CategoryDto;
import com.kabita.rms.servicesImpl.CategoryServiceImpl;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	@Autowired
	CategoryServiceImpl categoryService;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/post")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto category = categoryService.addCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(category, HttpStatus.CREATED);
	}

	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories() {
		return new ResponseEntity<List<CategoryDto>>(this.categoryService.getAllCategory(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable Integer categoryId) {
		CategoryDto category = this.categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Integer categoryId) {
		CategoryDto category = this.categoryService.getSingleCategory(categoryId);
		return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('AMDIN')")
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully!", true), HttpStatus.OK);
	}
}
