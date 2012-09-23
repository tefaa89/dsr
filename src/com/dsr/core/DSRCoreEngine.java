package com.dsr.core;

import java.io.File;
import java.util.Vector;
import weka.classifiers.lazy.IBk;
import com.dsr.classifier.DocumentClassifer;
import com.dsr.classifier.IBKDocumentClassifier;
import com.dsr.configuration.Config;
import com.dsr.database.DBConnection;
import com.dsr.database.DBQuery;
import com.dsr.instances.DocumentInfo;
import com.dsr.instances.DocumentInstance;
import com.dsr.instances.DocumentInstances;
import com.dsr.instances.DocumentInstancesBuilder;
import com.dsr.instances.DocumentInstancesInfo;
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

	public Vector<String> classifyFiles(DSRUnClassifiedFiles files) {
		DocumentsProcessing docProcess = new DocumentsProcessing(files.getPreprocessingVector());
		docProcess.process();
		Vector<DocumentInfo> docInfoVec = docProcess.getDocumentInfoVec();
		DocumentInstancesInfo docInstancesInfo = classifier.getDocInstancesInfo();
		DocumentInstancesBuilder docInstancesB = new DocumentInstancesBuilder(docInfoVec,
				docInstancesInfo, true);
		docInstancesB.setBuildFeatures(false);
		docInstancesB.buildInstances();
		Vector<String> classificationVec = classifier.classifyDocumentInstances(docInstancesB
				.getDocumentInstances());
		return classificationVec;
	}

	public void updateClassifier(DSRTrainingFiles newTrainingFiles) {
		Vector<DocumentInfo> docInfoVec;
		backupCurrentTrainedClassifier();

		DocumentsProcessing docProcess = new DocumentsProcessing(
				newTrainingFiles.getPreprocessingVector());
		docProcess.process();
		docInfoVec = docProcess.getDocumentInfoVec();

		// docInfoVec = tempDocInfoVec;

		DocumentInstancesBuilder docInstanceB = new DocumentInstancesBuilder(docInfoVec,
				classifier.getDocInstancesInfo(), true);
		if(classifier.isTrainedClassifierBool())
			docInstanceB.setBuildFeatures(false);
		else
			docInstanceB.setBuildFeatures(true);
		docInstanceB.buildInstances();
		classifier.updateClassifier(docInstanceB.getDocumentInstances());
	}

	public void trainClassifierFromDB() {
		Vector<DocumentInstance> trainingInstancesVec = DBQuery
				.getTrainingDocumentInstaces(DBConnection.connect());
		DocumentInstancesBuilder docInstancesBuilder = new DocumentInstancesBuilder(
				trainingInstancesVec);
	}

	private void backupCurrentTrainedClassifier() {

	}

	private boolean isClassifierObjectExists() {
		// TODO Check Database
		return false;
	}

	private boolean isClassifierModelExists() {
		File file = new File(Config.SERIALIZED_CLASSIFIER_MODEL_PATH);
		return file.exists();
	}
}
