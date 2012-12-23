package com.dsr.test;

import java.util.Vector;
import com.dsr.core.DSRCoreEngine;
import com.dsr.core.DSRTrainingFiles;
import com.dsr.core.DSRUnClassifiedFiles;
import com.dsr.instances.DocumentInfo;

public class OCRCoreEngineTest {
	public static void main(String[] args) {
		testUpdateClassifiedFilesOnEmptyClassifier();
	}

	public static void testUnclassifiedFilesOnEmptyClassifier() {
		DSRCoreEngine engine = new DSRCoreEngine();
		String s = "C:\\temp\\unclassified";
		DSRUnClassifiedFiles unclassFiles = new DSRUnClassifiedFiles(s);
		Vector<DocumentInfo> classes = engine.classifyFiles(unclassFiles);
		System.out.println(classes); // Should Return Null;
	}

	public static void testUpdateClassifiedFilesOnEmptyClassifier() {
		DSRCoreEngine engine = new DSRCoreEngine();

		String s = "C:\\temp\\classified";
		DSRTrainingFiles trainingFiles = new DSRTrainingFiles(s);
		engine.updateClassifier(trainingFiles);

		String s2 = "C:\\temp\\classified";
		DSRTrainingFiles trainingFiles2 = new DSRTrainingFiles(s2);
		engine.updateClassifier(trainingFiles2);

		String ss = "C:\\temp\\unclassified";
		DSRUnClassifiedFiles unclassFiles = new DSRUnClassifiedFiles(ss);
		Vector<DocumentInfo> classifiedDoc = engine.classifyFiles(unclassFiles);
		System.out.println(classifiedDoc);
	}

	public static void testRealData() {
		DSRCoreEngine engine = new DSRCoreEngine();

		String s = "C:\\Users\\TeFa\\Desktop\\Data\\UID\\Classified";
		DSRTrainingFiles trainingFiles = new DSRTrainingFiles(s);
		engine.updateClassifier(trainingFiles);

		String ss = "C:\\Users\\TeFa\\Desktop\\Data\\UID\\UnClassified";
		DSRUnClassifiedFiles unclassFiles = new DSRUnClassifiedFiles(ss);
		Vector<DocumentInfo> classifiedDoc = engine.classifyFiles(unclassFiles);
		System.out.println(classifiedDoc);

	}

	public static void testChangingAndRefreshing() {
		DSRCoreEngine engine = new DSRCoreEngine();

		DocumentInfo docInfo = new DocumentInfo(470, "My Doc.pdf", "Lelo", "Nothing Here aaaa");
		Vector<DocumentInfo> docInfoVec = new Vector<DocumentInfo>();
		docInfoVec.add(docInfo);

		engine.updateClassifier(docInfoVec);
	}

	/*
	 * - The classifier should be trained if there are effective instances in
	 * the database
	 *
	 *
	 */
}
