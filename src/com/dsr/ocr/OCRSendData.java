package com.dsr.ocr;

import com.dsr.util.Base64;

public class OCRSendData {
	private String fileName;
	private String category;
	private String encodedData;

	public OCRSendData(String fileName, byte[] bytes) {
		this(fileName, null, bytes);
	}

	public OCRSendData(String fileName, String category, byte[] bytes) {
		this.fileName = fileName;
		this.category = category;
		setEncodedData(bytes);
	}

	public OCRSendData(String fileName, String category, String encodedString) {
		this.fileName = fileName;
		this.category = category;
		encodedData = encodedString;
	}

	public void setEncodedData(byte[] bytes) {
		encodedData = Base64.encodeToString(bytes, false);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getEncodedData() {
		return encodedData;
	}

	public void setEncodedData(String encodedData) {
		this.encodedData = encodedData;
	}

	@Override
	public String toString() {
		return "[OCRSendData:\n    FileName: " + fileName + "\n    Category: " + category
				+ "\n    EncodedData: " + encodedData + "]";
	}
}
