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
		buildPreprocessingVector();
	}

	public DSRUnClassifiedFiles(String directory)
	{
		buildUnclassifiedFilesVec(directory);
		buildPreprocessingVector();
	}

	private void buildUnclassifiedFilesVec(String directory) {
		File directoryFile = new File(directory);
		unClassifiedFiles = convertFilesToVector(directoryFile.listFiles());
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

	private Vector<DSRFile> convertFilesToVector(File[] files) {
		Vector<DSRFile> dsrFilesVec = new Vector<DSRFile>();
		for (File file : files) {
			if (!file.isDirectory())
				dsrFilesVec.add(new DSRFile(file.getName(), FileUtil.getByteArrayForFile(file)));
		}
		return dsrFilesVec;
	}

	private void buildPreprocessingVector()
	{
		ocrSendDataVec = new Vector<OCRSendData>();
		for (DSRFile dsrFile : unClassifiedFiles)
			ocrSendDataVec.add(new OCRSendData(dsrFile.getFileName(), dsrFile.getBytes()));
	}

	public Vector<OCRSendData> getPreprocessingVector() {
		return ocrSendDataVec;
	}
}
