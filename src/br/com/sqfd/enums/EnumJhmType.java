package br.com.sqfd.enums;

public enum EnumJhmType {
	CLASS_MAPPING("class-mapping"),
	FLOW("flow"),
	REPORT("report");
	
	private String description;
	
	EnumJhmType(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return this.description;
	}
}
