package com.dsr.classifier;

import java.util.Vector;
import weka.classifiers.lazy.IBk;
import com.dsr.instances.DocumentInstances;
import com.dsr.instances.DocumentInstancesInfo;
import com.dsr.util.enumu.ClassifiersEnum;
import com.dsr.util.enumu.FeatureValuesEnum;
import com.dsr.util.enumu.NGramEnum;

@SuppressWarnings("serial")
public class IBKDocumentClassifier extends DocumentClassifer {
	private IBk ibkClassifier;

	public IBKDocumentClassifier() {
		ibkClassifier = null;
		setDocInstancesInfo(null);
		setTrainedClassifierBool(false);
	}

	public IBKDocumentClassifier(IBk ibkClassifier, NGramEnum nGramType,
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
	public Vector<String> classifyDocumentInstances(DocumentInstances docInstances) {
		return classifyDocumentInstances(docInstances,ibkClassifier);
	}

	@Override
	public void updateClassifier(DocumentInstances docInstances) {
		updateClassifier(docInstances, ibkClassifier);
	}
}
