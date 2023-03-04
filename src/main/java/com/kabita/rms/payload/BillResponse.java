package com.kabita.rms.payload;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BillResponse {
	private List<BillingDto> billingDto;
	private double total;
}
