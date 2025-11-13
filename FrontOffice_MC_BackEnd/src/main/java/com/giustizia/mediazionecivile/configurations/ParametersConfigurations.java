package com.giustizia.mediazionecivile.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class ParametersConfigurations {
	private  final String webPath = "";
    @Value("${rowsTable}")
	private String rowsTable;
    
	public String getWebPath() {
		return webPath;
	}
	public String getRowsTable() {
		return rowsTable;
	}



}