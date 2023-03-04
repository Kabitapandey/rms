package com.kabita.rms.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kabita.rms.entities.Category;
import com.kabita.rms.entities.Items;
import com.kabita.rms.exception.ResourceNotFoundException;
import com.kabita.rms.payload.ItemsDto;
import com.kabita.rms.repository.CategoryRepository;
import com.kabita.rms.repository.ItemsRepository;
import com.kabita.rms.services.ItemServices;

@Service
public class ItemServicesImpl implements ItemServices {
	@Autowired
	ItemsRepository itemRepo;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	CategoryRepository categoryRepo;

	@Override
	public ItemsDto addItems(ItemsDto itemDto, Integer categoryId) {
//		getting item details from user
		Items item = this.modelMapper.map(itemDto, Items.class);
//		getting category object from database
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
//saving data to the database
		item.setCategory(category);

		this.itemRepo.save(item);

		return this.modelMapper.map(item, ItemsDto.class);
	}

	@Override
	public List<ItemsDto> getAllItems() {
//		getting list of items
		List<Items> items = this.itemRepo.findAll();

		return items.stream().map((item) -> this.modelMapper.map(item, ItemsDto.class)).collect(Collectors.toList());
	}

	@Override
	public ItemsDto updateItem(ItemsDto itemDto, Integer itemId, Integer categoryId) {
//		getting item object
		Items item = this.itemRepo.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", itemId));
//		retrieving category object
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category", categoryId));
//setting the products value as default if null value is provided
		item.setProductName(itemDto.getProductName());
		item.setProductDesc(itemDto.getProductDesc());
		item.setPrice(itemDto.getPrice());
		item.setProductImg(itemDto.getProductImg());
		item.setCategory(category);
		item.setStock(itemDto.getStock());

		Items updatedItem = this.itemRepo.save(item);

		return this.modelMapper.map(updatedItem, ItemsDto.class);
	}

	@Override
	public void deleteItem(Integer itemId) {
//		retrieving items object
		Items item = this.itemRepo.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", itemId));

		this.itemRepo.delete(item);
	}

	@Override
	public ItemsDto getSingleItem(Integer itemId) {
		Items item = this.itemRepo.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", itemId));
		return this.modelMapper.map(item, ItemsDto.class);
	}

	@Override
	public List<ItemsDto> getItemByCategory(Integer categoryId) {
//		retrieving list of items as per their categories
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		List<Items> items = this.itemRepo.getByCategory(category);

		return items.stream().map((item) -> this.modelMapper.map(item, ItemsDto.class)).collect(Collectors.toList());
	}

	@Override
	public ItemsDto updateItemImg(ItemsDto postDto, Integer productId) {
		Items item = this.itemRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

		item.setProductImg(postDto.getProductImg());
		item.setCategory(item.getCategory());
		item.setPrice(item.getPrice());
		item.setProductName(item.getProductName());
		item.setProductDesc(item.getProductDesc());
		item.setStock(item.getStock());

		Items updatedItems = itemRepo.save(item);

		return this.modelMapper.map(updatedItems, ItemsDto.class);
	}

}
