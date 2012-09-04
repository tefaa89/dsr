package com.dsr.classifier;

import weka.classifiers.lazy.IBk;
import com.dsr.instances.DocumentInstance;
import com.dsr.instances.DocumentInstances;
import com.dsr.instances.DocumentInstancesInfo;
import com.dsr.util.enumu.FeatureValuesEnum;
import com.dsr.util.enumu.NGramEnum;

@SuppressWarnings("serial")
public class IBKDocumentClassifier extends DocumentClassifer {
	private IBk ibkClassifier;

	public IBKDocumentClassifier() {
		ibkClassifier = null;
		setDocInstancesInfo(null);
	}

	public IBKDocumentClassifier(IBk ibkClassifier, NGramEnum nGramType,
			FeatureValuesEnum featuresType) {
		this.ibkClassifier = ibkClassifier;
		DocumentInstancesInfo docInfo = new DocumentInstancesInfo();
		docInfo.setNGramType(nGramType);
		docInfo.setFeaturesType(featuresType);
		setDocInstancesInfo(docInfo);
	}

	public IBKDocumentClassifier(DocumentInstancesInfo docInstancesInfo) {
		ibkClassifier = null;
		setDocInstancesInfo(docInstancesInfo);
	}

	@Override
	public int classifyDocumentInstance(DocumentInstance docInstance) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] classifyDocumentInstances(DocumentInstances docInstances) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateClassifier(DocumentInstances docInstances) {
		updateClassifier(docInstances, ibkClassifier);
	}
}
