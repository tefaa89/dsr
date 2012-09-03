package com.dsr.core;

public class DSRFile {
	private String fileName;
	private byte[] bytes;
	
	public DSRFile(String fileName, byte[] bytes)
	{
		this.fileName = fileName;
		this.bytes = bytes;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
}
