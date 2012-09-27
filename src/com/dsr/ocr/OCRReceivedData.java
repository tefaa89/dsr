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

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setOcrString(String ocrString) {
		this.ocrString = ocrString;
	}

	@Override
	public String toString() {
		return "===> OCRReceivedData:\n\tFileName: " + fileName + "\n\tOCRString Size: " + ocrString.length();
	}
}
