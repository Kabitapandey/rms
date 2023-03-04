package com.kabita.rms.services;

import java.util.List;

import com.kabita.rms.payload.ItemsDto;

public interface ItemServices {
	ItemsDto addItems(ItemsDto itemDto, Integer categoryId);

	List<ItemsDto> getAllItems();

	ItemsDto updateItem(ItemsDto itemDto, Integer itemId, Integer categoryId);

	void deleteItem(Integer itemId);

	ItemsDto getSingleItem(Integer itemId);

	List<ItemsDto> getItemByCategory(Integer categoryId);

	ItemsDto updateItemImg(ItemsDto postDto,Integer productId);
}
