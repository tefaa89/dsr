package com.dsr.core;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import com.dsr.ocr.OCRSendData;
import com.dsr.util.FileUtil;

public class DSRTrainingFiles {
	private Vector<OCRSendData> ocrSendDataVec;
	private Map<String, Vector<DSRFile>> trainingFilesMap; // <Catergory, Files in
														// that Category>

	public DSRTrainingFiles() {

	}

	public DSRTrainingFiles(Map<String, Vector<DSRFile>> trainingFiles) {
		this.trainingFilesMap = trainingFiles;
	}

	public DSRTrainingFiles(String directory) {
		buildTrainingFilesMapfromDirectory(directory);
	}

	public void setTrainingFiles(Map<String, Vector<DSRFile>> trainingFiles) {
		this.trainingFilesMap = trainingFiles;
	}

	private void buildTrainingFilesMapfromDirectory(String directory) {
		trainingFilesMap = new HashMap<String, Vector<DSRFile>>();
		if (FileUtil.containsDirectories(directory)) {
			File[] categoryFolders = FileUtil.getDirectories(directory);
			for (File categoryFolder : categoryFolders) {
				Vector<DSRFile> trainingFilesVec = convertFilesToVector(categoryFolder.listFiles());
				trainingFilesMap.put(categoryFolder.getName(), trainingFilesVec);
			}
		} else {
			File currentDir = new File(directory);
			Vector<DSRFile> trainingFilesVec = convertFilesToVector(currentDir.listFiles());
			trainingFilesMap.put(currentDir.getName(), trainingFilesVec);
		}
	}

	private Vector<DSRFile> convertFilesToVector(File[] files) {
		Vector<DSRFile> dsrFilesVec = new Vector<DSRFile>();
		for (File file : files) {
			if (!file.isDirectory())
				dsrFilesVec.add(new DSRFile(file.getName(), FileUtil.getByteArrayForFile(file)));
		}
		return dsrFilesVec;
	}

	public Vector<OCRSendData> getPreprocessingVector() {
		ocrSendDataVec = new Vector<OCRSendData>();
		for (String key : trainingFilesMap.keySet()) {
			for (DSRFile dsrFile : trainingFilesMap.get(key))
				ocrSendDataVec.add(new OCRSendData(dsrFile.getFileName(), key, dsrFile.getBytes()));
		}
		return ocrSendDataVec;
	}
}
