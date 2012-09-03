package com.dsr.test;

import com.dsr.core.DSRCoreEngine;
import com.dsr.core.DSRTrainingFiles;

public class OCRCoreEngineTest {
	public static void main(String[] args) {
		DSRCoreEngine engine = new DSRCoreEngine();
		if(!engine.isClassifierTrainedFlag())
		{
			DSRTrainingFiles trainingFiles = new DSRTrainingFiles("C:\\temp");
			engine.updateClassifier(trainingFiles);
		}
		
		/*DSRUnClassifiedFiles filesToClassify = new DSRUnClassifiedFiles();
		File f1 = new File("");
		File f2 = new File("");
		filesToClassify.addFile(f1);
		filesToClassify.addFile(f2);
		
		engine.classifyFiles(filesToClassify);*/
	}
}
