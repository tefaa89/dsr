package com.dsr.ocr;

public class OCRReceivedData {
	private String fileName;
	private String category;
	private String ocrString;

	public OCRReceivedData(String fileName, String category, String ocrString) {
		this.fileName = fileName;
		this.category = category;
		this.ocrString = ocrString;
	}

	public String getFileName() {
		return fileName;
	}

	public String getCategory() {
		return category;
	}

	public String getOcrString() {
		return ocrString;
	}

	@Override
	public String toString() {
		return "### OCRReceivedData:\n    FileName: " + fileName + "\n    OCRString: " + ocrString;
	}
}
