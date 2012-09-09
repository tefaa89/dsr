package com.dsr.classifier;

import java.util.Vector;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;
import com.dsr.instances.DocumentInstance;
import com.dsr.instances.DocumentInstances;
import com.dsr.instances.DocumentInstancesInfo;
import com.dsr.util.DSRWekaUtil;
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
		setTrainedClassifierBool(false);
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
	public Vector<Integer> classifyDocumentInstances(DocumentInstances docInstances) {
		Instances instances = DSRWekaUtil.convertDocInstancesToWekaInstances(docInstances);
		Vector<Integer> res = new Vector<Integer>();
		for (Instance inst : instances) {
			try {
				res.add((int) ibkClassifier.classifyInstance(inst));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	@Override
	public void updateClassifier(DocumentInstances docInstances) {
		updateClassifier(docInstances, ibkClassifier);
	}
}
