package com.kabita.rms.controller;

import java.util.List;


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
import com.kabita.rms.payload.TablesDto;
import com.kabita.rms.services.TablesServices;

@RestController
@RequestMapping("/api")
public class TablesController {
	@Autowired
	TablesServices tableServices;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/tables")
	public ResponseEntity<TablesDto> addTables(@RequestBody TablesDto tablesDto) {
		return new ResponseEntity<TablesDto>(this.tableServices.addTable(tablesDto), HttpStatus.CREATED);
	}

	@GetMapping("/tables/get")
	public ResponseEntity<List<TablesDto>> getAllTables() {
		return new ResponseEntity<List<TablesDto>>(this.tableServices.getAllTables(), HttpStatus.OK);
	}

	@GetMapping("tables/{tableId}")
	public ResponseEntity<TablesDto> getSingleTable(@PathVariable Integer tableId) {
		return new ResponseEntity<TablesDto>(this.tableServices.getSingleTable(tableId), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("tables/{tableId}")
	public ResponseEntity<TablesDto> updateTable(@RequestBody TablesDto tablesDto, @PathVariable Integer tableId) {
		return new ResponseEntity<TablesDto>(this.tableServices.updateTable(tablesDto, tableId), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("tables/{tableId}")
	public ResponseEntity<ApiResponse> deleteTable(@PathVariable Integer tableId) {
		this.tableServices.deleteTable(tableId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Table deleted successfully", true), HttpStatus.OK);
	}
}
