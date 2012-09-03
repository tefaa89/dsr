package com.dsr.instances;

import java.io.Serializable;

public class DocumentInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String category;
	private String content;

	public DocumentInfo(String name, String category, String content){
		this.name = name;
		this.category = category;
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public String getContent() {
		return content;
	}

	@Override
	public String toString() {
		return "===> Name: " + name + "\n     Category: " + category + "\n     Content: " + content
				+ "\n ========================================================\n";
	}
}
