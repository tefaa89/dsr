package com.dsr.instances;

import java.io.Serializable;

public class DocumentInfo implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String category;
	private String content;

	public DocumentInfo(String name, String category, String content) {
		this.name = name;
		this.category = category;
		this.content = content;
	}

	public DocumentInfo(int id, String name, String category, String content) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.content = content;
	}

	public void setDocID(int id) {
		this.id = id;
	}

	public int getDocID() {
		return id;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		String res = "";
		res += "===>";
		res += "\tID: " + id + "\n";
		res += "\tName: " + name + "\n";
		res += "\tCategory: " + category + "\n";
		res += "\tContent Size: " + content.length() + "\n\n";
		return res;
	}
}
