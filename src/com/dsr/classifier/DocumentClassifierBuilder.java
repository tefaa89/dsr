package com.dsr.classifier;

import weka.core.Instances;
import com.dsr.instances.DocumentInstances;
import com.dsr.util.DSRWekaUtil;
import com.dsr.util.enumu.ClassifiersEnum;
import com.dsr.util.enumu.FeatureValuesEnum;

public class DocumentClassifierBuilder {
	private DocumentClassifer docClassifier;
	private ClassifiersEnum classifierType;
	private DocumentInstances docInstances;
	
	public DocumentClassifierBuilder(ClassifiersEnum classifierType, DocumentInstances docInstances) {
		this.classifierType = classifierType;
		this.docInstances = docInstances;
	}
	
	private void initClassifier() {
		if (classifierType == ClassifiersEnum.IBK)
			docClassifier = new IBKDocumentClassifier();
		else if (classifierType == ClassifiersEnum.NAIVE_BAYES)
			docClassifier = new NaiveBayesDocumentClassifier();
		docClassifier = null;
	}
	
	public void build()
	{
		initClassifier();
		trainClassifier();
	}
	
	private void trainClassifier() {
		Instances wekaInstances = DSRWekaUtil.convertDocInstancesToWekaInstances(docInstances, FeatureValuesEnum.TFIDF_VALUES);
		try {
		//	docClassifier.buildClassifier(wekaInstances);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DocumentClassifer getDocClassifier() {
		return docClassifier;
	}

	public void setDocClassifier(DocumentClassifer docClassifier) {
		this.docClassifier = docClassifier;
	}

	public ClassifiersEnum getClassifierType() {
		return classifierType;
	}

	public void setClassifierType(ClassifiersEnum classifierType) {
		this.classifierType = classifierType;
	}

	public DocumentInstances getDocInstances() {
		return docInstances;
	}

	public void setDocInstances(DocumentInstances docInstances) {
		this.docInstances = docInstances;
	}
}
