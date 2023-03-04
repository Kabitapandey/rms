package com.kabita.rms.payload;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartDto {
private int cartId;
@NotEmpty
private UserDto user;
@NotEmpty
private ItemsDto items;

private int quantity;
}
