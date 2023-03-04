package com.kabita.rms.services;

import java.util.List;

import com.kabita.rms.payload.TablesDto;

public interface TablesServices {
	TablesDto addTable(TablesDto tablesDto);

	List<TablesDto> getAllTables();

	TablesDto updateTable(TablesDto tablesDto, Integer tableId);

	void deleteTable(Integer tableId);

	TablesDto getSingleTable(Integer tableId);
}
