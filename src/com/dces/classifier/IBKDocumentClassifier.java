package com.dces.classifier;

import java.util.Vector;
import weka.classifiers.lazy.IBk;
import com.dces.instances.DocumentInfo;
import com.dces.instances.DocumentInstances;
import com.dces.instances.DocumentInstancesInfo;
import com.dces.util.enumu.ClassifiersEnum;
import com.dces.util.enumu.FeatureValuesEnum;
import com.dces.util.enumu.FeaturesModelEnum;

@SuppressWarnings("serial")
public class IBKDocumentClassifier extends DocumentClassifer {
	private IBk ibkClassifier;

	public IBKDocumentClassifier() {
		ibkClassifier = null;
		setDocInstancesInfo(null);
		setTrainedClassifierBool(false);
	}

	public IBKDocumentClassifier(IBk ibkClassifier, FeaturesModelEnum nGramType,
			FeatureValuesEnum featuresType) {
		this.ibkClassifier = ibkClassifier;
		DocumentInstancesInfo docInfo = new DocumentInstancesInfo();
		docInfo.setNGramType(nGramType);
		docInfo.setFeaturesType(featuresType);
		setClassifiersEnum(ClassifiersEnum.IBK);
		setDocInstancesInfo(docInfo);
		setTrainedClassifierBool(false);
	}

	public IBKDocumentClassifier(DocumentInstancesInfo docInstancesInfo) {
		ibkClassifier = null;
		setDocInstancesInfo(docInstancesInfo);
	}

	@Override
	public Vector<DocumentInfo> classifyDocumentInstances(DocumentInstances docInstances) {
		return classifyDocumentInstances(docInstances,ibkClassifier);
	}

	@Override
	public void updateClassifier(DocumentInstances docInstances) {
		updateClassifier(docInstances, ibkClassifier);
	}

	@Override
	public void refreshFromDB() {
		refreshFromDB(ibkClassifier);
	}
}
