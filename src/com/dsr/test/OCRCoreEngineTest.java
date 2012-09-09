package com.dsr.test;

import com.dsr.core.DSRCoreEngine;
import com.dsr.core.DSRUnClassifiedFiles;

public class OCRCoreEngineTest {
	public static void main(String[] args) {

		// Vector<DocumentInfo> docInfoVec = FileUtil
		// .loadObjectFromFile("resources\\obj\\documentsInfoTrainingData.obj");
		DSRCoreEngine engine = new DSRCoreEngine();
		// engine.updateClassifier(null, docInfoVec);
		String s = "C:\\temp\\unclassified";
		DSRUnClassifiedFiles unclassFiles = new DSRUnClassifiedFiles(s);
		int[] classes = engine.classifyFiles(unclassFiles);
		System.out.println(classes[0]);
	}
}
