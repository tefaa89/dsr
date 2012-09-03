package com.dsr.core;

import java.io.File;
import java.util.Vector;
import com.dsr.ocr.OCRSendData;
import com.dsr.util.FileUtil;

public class DSRUnClassifiedFiles {
	private Vector<OCRSendData> ocrSendDataVec;
	private Vector<DSRFile> unClassifiedFiles;

	public DSRUnClassifiedFiles() {
		unClassifiedFiles = new Vector<DSRFile>();
	}

	public DSRUnClassifiedFiles(Vector<DSRFile> unClassifiedFiles) {
		this.unClassifiedFiles = unClassifiedFiles;
	}

	public Vector<DSRFile> getUnClassifiedFiles() {
		return unClassifiedFiles;
	}

	public void setUnClassifiedFiles(Vector<DSRFile> unClassifiedFiles) {
		this.unClassifiedFiles = unClassifiedFiles;
	}

	public void addFile(File file) {
		unClassifiedFiles.add(new DSRFile(file.getName(), FileUtil.getByteArrayForFile(file)));
	}

	public void addFile(String fileName, byte[] bytes) {
		unClassifiedFiles.add(new DSRFile(fileName, bytes));
	}

	public Vector<OCRSendData> getPreprocessingVector() {
		ocrSendDataVec = new Vector<OCRSendData>();
		for (DSRFile dsrFile : unClassifiedFiles)
			ocrSendDataVec.add(new OCRSendData(dsrFile.getFileName(), dsrFile.getBytes()));
		return ocrSendDataVec;
	}
}
