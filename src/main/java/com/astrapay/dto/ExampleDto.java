package com.astrapay.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ExampleDto {
    @NotEmpty
    private String name;
    private String description;
    
    public void setName(String input) {
    	this.name = input;
    }
    
    public void setDescription(String input) {
    	this.description = input;
    }

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}
}