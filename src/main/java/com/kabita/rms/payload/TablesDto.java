package com.kabita.rms.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TablesDto {
private int tableId;
private boolean booked;
private float price;
}
