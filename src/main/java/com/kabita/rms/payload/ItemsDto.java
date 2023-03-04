package com.kabita.rms.payload;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemsDto {
	private int productId;
	@NotEmpty
	private String productName;
	private String productDesc;
	@NotEmpty
	private String productImg;

	private float price;

	private CategoryDto category;
	private int stock;
}
