package com.dsr.test;

import java.util.Vector;
import com.dsr.core.DSRCoreEngine;
import com.dsr.instances.DocumentInfo;
import com.dsr.util.FileUtil;

public class OCRCoreEngineTest {
	public static void main(String[] args) {
		Vector<DocumentInfo> docInfoVec = FileUtil
				.loadObjectFromFile("resources\\obj\\documentsInfoTrainingData.obj");
		DSRCoreEngine engine = new DSRCoreEngine();
		engine.updateClassifier(null,docInfoVec);

		/*
		 * DSRUnClassifiedFiles filesToClassify = new DSRUnClassifiedFiles();
		 * File f1 = new File(""); File f2 = new File("");
		 * filesToClassify.addFile(f1); filesToClassify.addFile(f2);
		 *
		 * engine.classifyFiles(filesToClassify);
		 */
	}

}
