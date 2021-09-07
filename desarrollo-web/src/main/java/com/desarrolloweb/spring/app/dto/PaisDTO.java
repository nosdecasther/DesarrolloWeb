package com.desarrolloweb.spring.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class PaisDTO {

	private String name;
	private MonedaDTO currencies;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MonedaDTO getCurrencies() {
		return currencies;
	}

	public void setCurrencies(MonedaDTO currencies) {
		this.currencies = currencies;
	}

}
