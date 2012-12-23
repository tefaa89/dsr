package com.dsr.core;

import java.util.Vector;
import weka.classifiers.lazy.IBk;
import com.dsr.classifier.DocumentClassifer;
import com.dsr.classifier.IBKDocumentClassifier;
import com.dsr.configuration.Config;
import com.dsr.database.DBConnection;
import com.dsr.database.DBQuery;
import com.dsr.instances.DocumentInfo;
import com.dsr.instances.DocumentInstancesBuilder;
import com.dsr.instances.DocumentInstancesInfo;
import com.dsr.ocr.OCRService;
import com.dsr.preprocessing.DocumentsProcessing;
import com.dsr.util.Trace;
import com.dsr.util.enumu.FeatureValuesEnum;
import com.dsr.util.enumu.NGramEnum;

public class DSRCoreEngine {
	private DocumentClassifer classifier;
	private boolean isConnected;

	public DSRCoreEngine() {
		Config.initConfiguration();
		checkConnections();
		if (isConnected) {
			checkDB();
			initClassifier();
		}
	}

	private void checkConnections() {
		isConnected = false;
		boolean mysqlBool = DBConnection.checkConnection();
		boolean ocrBool = OCRService.checkConnection();
		isConnected = mysqlBool && ocrBool;
	}

	private void checkDB() {
		boolean databaseExists = DBQuery.isDBExists(DBConnection.connect());
		if (!databaseExists)
			DBQuery.createDB(DBConnection.connect());
		DBConnection.setConnectToDBTable(true);
		boolean tablesExists = DBQuery.tablesExists(DBConnection.connect());
		if (!tablesExists)
			DBQuery.createTables(DBConnection.connect());
	}

	private void initClassifier() {
		classifier = fetchClassifierFromDB();
		if (classifier == null) {
			classifier = new IBKDocumentClassifier(new IBk(), NGramEnum.WORD_UNIGRAM,
					FeatureValuesEnum.TF_VALUES);
			backupCurrentTrainedClassifier();
		}
	}

	public boolean isConnected() {
		return isConnected;
	}

	public Vector<DocumentInfo> classifyFiles(DSRUnClassifiedFiles files) {
		Trace.trace("Classifier: " + "Classifying " + files.getUnClassifiedFiles().size()
				+ " Files");
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
		Vector<DocumentInfo> classificationVec = classifier.classifyDocumentInstances(docInstancesB
				.getDocumentInstances());
		return classificationVec;
	}

	public void updateClassifier(DSRTrainingFiles newTrainingFiles) {
		Trace.trace("Classifier: " + "Updating " + newTrainingFiles.getPreprocessingVector().size()
				+ " Files");
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

	public void updateClassifier(Vector<DocumentInfo> docInfoVec) {
		// For existing documents
		// 1 - Update the new Category in DB
		// 2 - Update instance effectiveness in DB
		// 3 - ReFresh Classifier
		DBQuery.updateCategories(DBConnection.connect(), docInfoVec);
		DBQuery.updateInstanceEffectiveness(DBConnection.connect(), docInfoVec);
		classifier.refreshFromDB();
	}

	public void clear() {
		DBQuery.dropDatabase(DBConnection.connect());
	}

	public Vector<DocumentInfo> getAllDocumentsInfo() {
		Vector<DocumentInfo> docInfoVec = DBQuery.getAllDocuments(DBConnection.connect());
		return docInfoVec;
	}

	private void backupCurrentTrainedClassifier() {
		DBQuery.backupClassifier(DBConnection.connect(), classifier);
	}

	private DocumentClassifer fetchClassifierFromDB() {
		return DBQuery.getLatestClassifier(DBConnection.connect());
	}
}
