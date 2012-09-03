package com.dsr.core;

import java.io.File;
import java.util.Vector;
import com.dsr.classifier.DocumentClassifer;
import com.dsr.configuration.Config;
import com.dsr.instances.DocumentInfo;
import com.dsr.instances.DocumentInstancesBuilder;
import com.dsr.preprocessing.DocumentsProcessing;
import com.dsr.util.FileUtil;

public class DSRCoreEngine {
	private DocumentClassifer classifier;

	public DSRCoreEngine() {
		initClassifier();
	}

	private void initClassifier() {
		try {
			if (!isClassifierObjectExists())
				if (isClassifierModelExists()) {
					classifier = FileUtil
							.loadObjectFromFile(Config.SERIALIZED_CLASSIFIER_MODEL_PATH);
				} else {
					throw new Exception("Can't Find Serialized Classifier Model: "
							+ Config.SERIALIZED_CLASSIFIER_MODEL_PATH);
				}
			else {
				classifier = FileUtil.loadObjectFromFile(Config.SERIALIZED_CLASSIFIER_OBJECT_PATH);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int[] classifyFiles(DSRUnClassifiedFiles files) {
		DocumentsProcessing docProcess = new DocumentsProcessing(files.getPreprocessingVector());
		docProcess.process();
		Vector<DocumentInfo> docInfoVec = docProcess.getDocumentInfoVec();
		DocumentInstancesBuilder docInstancesB = new DocumentInstancesBuilder(docInfoVec,
				classifier.getDocInstancesInfo().getNGramType());
		docInstancesB.buildInstances();
		int[] classificationArray = classifier.classifyDocumentInstances(docInstancesB
				.getDocumentInstances());
		return classificationArray;
	}

	public void updateClassifier(DSRTrainingFiles newTrainingFiles) {
		Vector<DocumentInfo> docInfoVec;
		if (classifier.isClassifierTrainined()) {
			backupCurrentTrainedClassifier();
			DocumentsProcessing docProcess = new DocumentsProcessing(
					newTrainingFiles.getPreprocessingVector());
			docInfoVec = docProcess.getDocumentInfoVec();
			docProcess.process();
		} else {
			// Load Training Files
			docInfoVec = FileUtil.loadObjectFromFile("documentsInfoTrainingData.obj");
		}

		DocumentInstancesBuilder docInstanceB = new DocumentInstancesBuilder(docInfoVec, null);
		docInstanceB.buildInstances();
		classifier.updateClassifier(docInstanceB.getDocumentInstances());
		FileUtil.writeObjectToFile(classifier, Config.SERIALIZED_CLASSIFIER_OBJECT_PATH);
	}

	private void backupCurrentTrainedClassifier() {
		/*
		 * 1- Check old backups naming 2- Rename
		 * Config.SERIALIZED_CLASSIFIER_OBJECT_PATH
		 */
	}

	private boolean isClassifierObjectExists() {
		File file = new File(Config.SERIALIZED_CLASSIFIER_OBJECT_PATH);
		return file.exists();
	}

	private boolean isClassifierModelExists() {
		File file = new File(Config.SERIALIZED_CLASSIFIER_MODEL_PATH);
		return file.exists();
	}
}
