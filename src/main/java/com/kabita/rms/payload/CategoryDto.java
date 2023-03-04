package com.kabita.rms.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
	private int categoryId;
	@NotEmpty
	@Size(max = 50)
	private String categoryName;
}
