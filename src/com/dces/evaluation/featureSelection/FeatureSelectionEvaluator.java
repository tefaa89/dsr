package com.dces.evaluation.featureSelection;

import java.util.ArrayList;
import java.util.Map;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import com.dces.core.DEEvaluationLog;
import com.dces.evaluation.DEInstances;
import com.dces.evaluation.EvaluationInfo;
import com.dces.evaluation.classifiers.DocumentClassifer;

public class FeatureSelectionEvaluator {
	private static Logger logger = (Logger) LoggerFactory
			.getLogger(FeatureSelectionEvaluator.class);
	private ArrayList<DocumentFeatureSelectionFilter> featureSelectionFilterList;
	private ArrayList<DocumentClassifer> classifierList;
	/**
	 * evaluationInfoMap holds the evaluation list for every classifier. Every
	 * filtered feature vector will be evaluated on all classifiers and then
	 * stored in the evaluationInfoMap.
	 */
	private Map<DocumentClassifer, ArrayList<EvaluationInfo>> evaluationInfoMap;
	private ArrayList<EvaluationInfo> evaluationInfoResultList;

	public FeatureSelectionEvaluator() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<DocumentFeatureSelectionFilter> getFeatureSelectionFilterList() {
		return featureSelectionFilterList;
	}

	public void setFeatureSelectionFilterList(
			ArrayList<DocumentFeatureSelectionFilter> featureSelectionFilterList) {
		this.featureSelectionFilterList = featureSelectionFilterList;
	}

	public void setClassifierList(ArrayList<DocumentClassifer> classifierList) {
		logger.trace("Feature Selecton Evaluator Classifiers are {}", classifierList);
		this.classifierList = classifierList;
	}

	public void updateEvaluationLog(DEEvaluationLog evalLog) {

	}

	public ArrayList<EvaluationInfo> getEvaluationInfoResultList() {
		return evaluationInfoResultList;
	}

	public void evaluate(DEInstances deInstances) {
		for (DocumentFeatureSelectionFilter fsFilter : featureSelectionFilterList) {
			// Apply filter on deInstances and then evaluate the instance with
			// all classifiers.
		}
	}
}
