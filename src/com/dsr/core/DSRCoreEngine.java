package com.dsr.core;

import java.io.File;
import java.util.Vector;
import weka.classifiers.lazy.IBk;
import com.dsr.classifier.DocumentClassifer;
import com.dsr.classifier.IBKDocumentClassifier;
import com.dsr.configuration.Config;
import com.dsr.instances.DocumentInfo;
import com.dsr.instances.DocumentInstancesBuilder;
import com.dsr.preprocessing.DocumentsProcessing;
import com.dsr.util.FileUtil;
import com.dsr.util.enumu.FeatureValuesEnum;
import com.dsr.util.enumu.NGramEnum;

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
					classifier = new IBKDocumentClassifier(new IBk(), NGramEnum.WORD_UNIGRAM,
							FeatureValuesEnum.TFIDF_VALUES);
					/*
					 * throw new
					 * Exception("Can't Find Serialized Classifier Model: " +
					 * Config.SERIALIZED_CLASSIFIER_MODEL_PATH);
					 */
				}
			else {
				classifier = FileUtil.loadObjectFromFile(Config.SERIALIZED_CLASSIFIER_OBJECT_PATH);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Vector<Integer> classifyFiles(DSRUnClassifiedFiles files) {
		DocumentsProcessing docProcess = new DocumentsProcessing(files.getPreprocessingVector());
		docProcess.process();
		Vector<DocumentInfo> docInfoVec = docProcess.getDocumentInfoVec();
		DocumentInstancesBuilder docInstancesB = new DocumentInstancesBuilder(docInfoVec,
				classifier.getDocInstancesInfo(), true);
		docInstancesB.setBuildFeatures(false);
		docInstancesB.buildInstances();
		Vector<Integer> classificationVec = classifier.classifyDocumentInstances(docInstancesB
				.getDocumentInstances());
		return classificationVec;
	}

	public void updateClassifier(DSRTrainingFiles newTrainingFiles,
			Vector<DocumentInfo> tempDocInfoVec) {
		Vector<DocumentInfo> docInfoVec;
		backupCurrentTrainedClassifier();
		/*
		 * DocumentsProcessing docProcess = new DocumentsProcessing(
		 * newTrainingFiles.getPreprocessingVector()); docProcess.process();
		 * docInfoVec = docProcess.getDocumentInfoVec();
		 */
		docInfoVec = tempDocInfoVec;

		DocumentInstancesBuilder docInstanceB = new DocumentInstancesBuilder(docInfoVec,
				classifier.getDocInstancesInfo(),true);
		docInstanceB.setBuildFeatures(false);
		docInstanceB.buildInstances();
		classifier.updateClassifier(docInstanceB.getDocumentInstances());
		//FileUtil.writeObjectToFile(classifier, Config.SERIALIZED_CLASSIFIER_OBJECT_PATH);
	}

	private void backupCurrentTrainedClassifier() {

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
