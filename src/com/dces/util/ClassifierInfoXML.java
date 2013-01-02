package com.dces.util;

import java.util.Map;

public class ClassifierInfoXML {
	private String id;
	private String className;
	private Map<String, String[]> parameters;

	public ClassifierInfoXML() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Map<String, String[]> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String[]> parameters) {
		this.parameters = parameters;
	}
}
