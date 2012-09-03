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
	
	public IBKDocumentClassifier(IBk ibkClassifier, NGramEnum nGramType, FeatureValuesEnum featuresType)
	{
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
		/*
		 * 1- Check for new category
		 * 2- If new category found continue, else go to step
		 * 3- Load old training data (All Document Instance entries in DB)
		 * 4- Add old training data to new training data
		 * 5- Build training instances
		 * 6- Train Classifier
		 * 7- Update DocumentInstancesInfo
		 */
		
		
	}

}
