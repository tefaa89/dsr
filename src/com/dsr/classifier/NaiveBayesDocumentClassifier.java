package com.dsr.classifier;

import weka.classifiers.bayes.NaiveBayes;
import com.dsr.instances.DocumentInstance;
import com.dsr.instances.DocumentInstances;


@SuppressWarnings("serial")
public class NaiveBayesDocumentClassifier extends DocumentClassifer{
	NaiveBayes naiveBayesClassifier;
	public NaiveBayesDocumentClassifier()
	{
		
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
		// TODO Auto-generated method stub
		
	}
}
