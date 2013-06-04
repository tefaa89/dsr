package com.esda.test;

import java.util.Enumeration;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.core.Instances;
import weka.core.OptionHandler;
import com.esda.evaluation.featureExtraction.FeatureExtractorFilterAbstract;


public class CustomExtractionFilter extends ASEvaluation {

	@Override
	public void buildEvaluator(Instances arg0) throws Exception {
		// TODO Auto-generated method stub
		InfoGainAttributeEval f;
		CfsSubsetEval c;
	}



}
