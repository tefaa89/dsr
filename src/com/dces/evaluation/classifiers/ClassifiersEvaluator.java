package com.dces.evaluation.classifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.dces.core.DEEvaluationLog;
import com.dces.evaluation.DEInstances;
import com.dces.evaluation.EvaluationInfo;
import com.dces.evaluation.featureSelection.DocumentFeatureSelectionFilter;

public class ClassifiersEvaluator {
	/**
	 * featureSelectionFilterMap holds the best document feature selection
	 * filter for every classifier string path
	 */
	private Map<String, DocumentFeatureSelectionFilter> featureSelectionFilterMap;
	private ArrayList<DocumentClassifer> classifierList;
	
	public ClassifiersEvaluator() {
		
	}
	
	public void setClassifierList(ArrayList<DocumentClassifer> classifierList)
	{
		this.classifierList = classifierList;
	}
	
	public void setFeatureSelectionFilterMap(ArrayList<EvaluationInfo> evalInfo)
	{
		featureSelectionFilterMap = new HashMap<String, DocumentFeatureSelectionFilter>();
	}
	
	public void updateEvaluationLog(DEEvaluationLog evaLog)
	{
		
	}
	
	public void evaluate(DEInstances deInstances) {

	}
}
