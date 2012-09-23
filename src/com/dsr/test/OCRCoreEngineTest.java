package com.dsr.test;

import java.util.Vector;
import com.dsr.core.DSRCoreEngine;
import com.dsr.core.DSRTrainingFiles;
import com.dsr.core.DSRUnClassifiedFiles;
import com.dsr.util.enumu.FeatureValuesEnum;

public class OCRCoreEngineTest {
	public static void main(String[] args) {

		// Vector<DocumentInfo> docInfoVec = FileUtil
		// .loadObjectFromFile("resources\\obj\\documentsInfoTrainingData.obj");
		// engine.trainClassifierFromDB();
		testUpdateClassifiedFilesOnEmptyClassifier();
	}

	// TODO: When I get a new class I should remove all instances from the
	// Database and re-build them

	public static void testUnclassifiedFilesOnEmptyClassifier() {
		DSRCoreEngine engine = new DSRCoreEngine();
		String s = "C:\\temp\\unclassified";
		DSRUnClassifiedFiles unclassFiles = new DSRUnClassifiedFiles(s);
		Vector<String> classes = engine.classifyFiles(unclassFiles);
		System.out.println(classes); //Should Return Null;
	}

	public static void testUpdateClassifiedFilesOnEmptyClassifier()
	{
		DSRCoreEngine engine = new DSRCoreEngine();
		String s = "C:\\temp\\classified";
		DSRTrainingFiles trainingFiles = new DSRTrainingFiles(s);
		engine.updateClassifier(trainingFiles);

		String ss = "C:\\temp\\unclassified";
		DSRUnClassifiedFiles unclassFiles = new DSRUnClassifiedFiles(ss);
		Vector<String> classes = engine.classifyFiles(unclassFiles);
		System.out.println(classes);
	}
}
