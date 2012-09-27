package com.dsr.core;

import java.util.Vector;
import weka.classifiers.lazy.IBk;
import com.dsr.classifier.DocumentClassifer;
import com.dsr.classifier.IBKDocumentClassifier;
import com.dsr.database.DBConnection;
import com.dsr.database.DBQuery;
import com.dsr.instances.DocumentInfo;
import com.dsr.instances.DocumentInstancesBuilder;
import com.dsr.instances.DocumentInstancesInfo;
import com.dsr.preprocessing.DocumentsProcessing;
import com.dsr.util.Trace;
import com.dsr.util.enumu.FeatureValuesEnum;
import com.dsr.util.enumu.NGramEnum;

public class DSRCoreEngine {
	private DocumentClassifer classifier;

	public DSRCoreEngine() {
		initClassifier();
	}

	private void initClassifier() {
		classifier = fetchClassifierFromDB();
		if (classifier == null)
		{
			classifier = new IBKDocumentClassifier(new IBk(), NGramEnum.WORD_UNIGRAM,
					FeatureValuesEnum.TF_VALUES);
			backupCurrentTrainedClassifier();
		}
	}

	public Vector<String> classifyFiles(DSRUnClassifiedFiles files) {
		Trace.trace("Classifier: " + "Classifying " + files.getUnClassifiedFiles().size() + " Files");
		DocumentsProcessing docProcess = new DocumentsProcessing(files.getPreprocessingVector());
		docProcess.process();
		Vector<DocumentInfo> docInfoVec = docProcess.getDocumentInfoVec();
		DocumentInstancesInfo docInstancesInfo = classifier.getDocInstancesInfo();
		DocumentInstancesBuilder docInstancesB = new DocumentInstancesBuilder(docInfoVec,
				docInstancesInfo, true);
		if (classifier.isTrainedClassifierBool())
			docInstancesB.setBuildFeatures(false);
		else
			docInstancesB.setBuildFeatures(true);
		docInstancesB.buildInstances();
		Vector<String> classificationVec = classifier.classifyDocumentInstances(docInstancesB
				.getDocumentInstances());
		return classificationVec;
	}

	public void updateClassifier(DSRTrainingFiles newTrainingFiles) {
		Trace.trace("Classifier: " + "Updating " + newTrainingFiles.getPreprocessingVector().size() + " Files");
		Vector<DocumentInfo> docInfoVec;
		DocumentsProcessing docProcess = new DocumentsProcessing(
				newTrainingFiles.getPreprocessingVector());
		docProcess.process();
		docInfoVec = docProcess.getDocumentInfoVec();

		// docInfoVec = tempDocInfoVec;

		DocumentInstancesBuilder docInstanceB = new DocumentInstancesBuilder(docInfoVec,
				classifier.getDocInstancesInfo(), true);
		if (classifier.isTrainedClassifierBool())
			docInstanceB.setBuildFeatures(false);
		else
			docInstanceB.setBuildFeatures(true);
		docInstanceB.setEffictiveInstances(true);
		docInstanceB.buildInstances();
		classifier.updateClassifier(docInstanceB.getDocumentInstances());
		backupCurrentTrainedClassifier();
	}

	private void backupCurrentTrainedClassifier() {
		DBQuery.backupClassifier(DBConnection.connect(), classifier);
	}

	private DocumentClassifer fetchClassifierFromDB() {
		return DBQuery.getLatestClassifier(DBConnection.connect());
	}
}
