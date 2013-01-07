package com.dces.evaluation.featureSelection;

import java.util.ArrayList;
import com.dces.core.DEEvaluationLog;
import com.dces.evaluation.DEInstances;
import com.dces.evaluation.classifiers.DocumentClassifer;

public class FeatureSelectionEvaluator {
	private DEEvaluationLog evalLog;
	private ArrayList<DocumentFeatureSelectionFilter>  featureSelectionFilterList;
	private ArrayList<DocumentClassifer> classifierList;
	
	public FeatureSelectionEvaluator() {
		// TODO Auto-generated constructor stub
	}
	
	public DEEvaluationLog getEvaluationLog() {
		return evalLog;
	}

	public void setEvaluationLog(DEEvaluationLog evalLog) {
		this.evalLog = evalLog;
	}

	public ArrayList<DocumentFeatureSelectionFilter> getFeatureSelectionFilterList() {
		return featureSelectionFilterList;
	}

	public void setFeatureSelectionFilterList(
			ArrayList<DocumentFeatureSelectionFilter> featureSelectionFilterList) {
		this.featureSelectionFilterList = featureSelectionFilterList;
	}

	public ArrayList<DocumentClassifer> getClassifierList() {
		return classifierList;
	}

	public void setClassifierList(ArrayList<DocumentClassifer> classifierList) {
		this.classifierList = classifierList;
	}

	public void evaluate(DEInstances deInstances)
	{
		
	}
}
