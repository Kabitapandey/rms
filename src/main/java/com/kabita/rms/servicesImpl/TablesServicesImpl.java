package com.kabita.rms.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kabita.rms.entities.Tables;
import com.kabita.rms.exception.ResourceNotFoundException;
import com.kabita.rms.payload.TablesDto;
import com.kabita.rms.repository.TableRepository;
import com.kabita.rms.services.TablesServices;

@Service
public class TablesServicesImpl implements TablesServices {
	@Autowired
	TableRepository tableRepo;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public TablesDto addTable(TablesDto tablesDto) {
		Tables table = this.modelMapper.map(tablesDto, Tables.class);
		table.setBooked(tablesDto.isBooked());

		return this.modelMapper.map(this.tableRepo.save(table), TablesDto.class);
	}

	@Override
	public List<TablesDto> getAllTables() {
		List<Tables> tables = this.tableRepo.findAll();

		return tables.stream().map((table) -> this.modelMapper.map(table, TablesDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public TablesDto updateTable(TablesDto tablesDto, Integer tableId) {
		Tables table = this.tableRepo.findById(tableId)
				.orElseThrow(() -> new ResourceNotFoundException("Table", "Table Id", tableId));

		table.setPrice(tablesDto.getPrice());
		table.setBooked(tablesDto.isBooked());

		Tables updatedTable = this.tableRepo.save(table);
		return this.modelMapper.map(updatedTable, TablesDto.class);
	}

	@Override
	public TablesDto getSingleTable(Integer tableId) {
		Tables table = this.tableRepo.findById(tableId)
				.orElseThrow(() -> new ResourceNotFoundException("Table", "Table Id", tableId));
		return this.modelMapper.map(table, TablesDto.class);
	}

	@Override
	public void deleteTable(Integer tableId) {
		Tables table = this.tableRepo.findById(tableId)
				.orElseThrow(() -> new ResourceNotFoundException("Table", "Table Id", tableId));

		this.tableRepo.delete(table);
	}

}
