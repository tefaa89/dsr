package com.dces.evaluation.featureSelection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import com.dces.evaluation.DEInstances;
import com.dces.evaluation.EvaluationInfo;
import com.dces.evaluation.EvaluatorAbstract;
import com.dces.evaluation.classifiers.ClassifiersEvaluator;
import com.dces.evaluation.classifiers.DocumentClassifer;

public class FeatureSelectionEvaluator extends EvaluatorAbstract {
	private static Logger logger = (Logger) LoggerFactory
			.getLogger(FeatureSelectionEvaluator.class);
	/**
	 * evaluationInfoMap holds the evaluation list for every classifier string
	 * path. Every filtered feature vector will be evaluated on all classifiers
	 * and then stored in the evaluationInfoMap.
	 */
	private Map<String, ArrayList<EvaluationInfo>> evaluationInfoMap;
	private ArrayList<DocumentFeatureSelectionFilter> featureSelectionFilterList;
	private ArrayList<DocumentClassifer> classifierList;

	public FeatureSelectionEvaluator() {
		evaluationInfoMap = new HashMap<String, ArrayList<EvaluationInfo>>();
	}

	public ArrayList<DocumentFeatureSelectionFilter> getFeatureSelectionFilterList() {
		return featureSelectionFilterList;
	}

	public Map<String, DocumentFeatureSelectionFilter> getBestClassifiersFSMap() {
		// The evaluationInfoList in the parent class contains the best
		// evaluations only
		Map<String, DocumentFeatureSelectionFilter> map = new HashMap<String, DocumentFeatureSelectionFilter>();
		for (EvaluationInfo evalInfo : evaluationInfoResultList) {
			String classifierClassPath = evalInfo.getEvalParameters().getClassifier()
					.getClassPath();
			DocumentFeatureSelectionFilter fsF = evalInfo.getEvalParameters().getFeatureSelection();
			map.put(classifierClassPath, fsF);
		}
		return map;
	}

	public void setFeatureSelectionFilterList(
			ArrayList<DocumentFeatureSelectionFilter> featureSelectionFilterList) {
		this.featureSelectionFilterList = featureSelectionFilterList;
	}

	public void setClassifierList(ArrayList<DocumentClassifer> classifierList) {
		logger.trace("Feature Selecton Evaluator Classifiers are {}", classifierList);
		this.classifierList = classifierList;
	}

	private Map<String, ArrayList<DocumentClassifer>> getClassifiersMap() {
		Map<String, ArrayList<DocumentClassifer>> classifiersMap = new HashMap<String, ArrayList<DocumentClassifer>>();
		for (DocumentClassifer classifier : classifierList) {
			String key = classifier.getClassPath();
			if (!classifiersMap.containsKey(key))
				classifiersMap.put(key, new ArrayList<DocumentClassifer>());
			classifiersMap.get(key).add(classifier);
		}
		return classifiersMap;
	}

	private EvaluationInfo getBestEvaluation(ArrayList<EvaluationInfo> evalInfoList) {
		EvaluationInfo res = evalInfoList.remove(0);
		for (EvaluationInfo evalInfo : evalInfoList) {
			double currentAccuracy = evalInfo.getEvalResults().getAccuracy();
			double prevAccuracy = res.getEvalResults().getAccuracy();
			if (currentAccuracy > prevAccuracy)
				res = evalInfo;
			else if (currentAccuracy == prevAccuracy) {
				int currentNumOfAttr = evalInfo.getEvalParameters().getSelectedAttributes().size();
				int prevNumOfAttr = res.getEvalParameters().getSelectedAttributes().size();
				if (currentNumOfAttr < prevNumOfAttr)
					res = evalInfo;
			}
		}
		return res;
	}

	/**
	 * Updates the evaluation list in the parent class with best feature
	 * selection for each classifier from the evaluations collected in
	 * evaluationInfoMap
	 */
	private void updateEvaluationList() {
		for (String key : evaluationInfoMap.keySet()) {
			EvaluationInfo evalInfo = getBestEvaluation(evaluationInfoMap.get(key));
			updateEvaluationInfo(evalInfo);
		}
	}

	@Override
	public void clear() {
		super.clear();
		evaluationInfoMap = new HashMap<String, ArrayList<EvaluationInfo>>();
	}

	public void evaluate(DEInstances deInstances) {
		Map<String, ArrayList<DocumentClassifer>> classifiersMap = getClassifiersMap();
		for (DocumentFeatureSelectionFilter fsFilter : featureSelectionFilterList) {
			// Apply filter on deInstances and then evaluate the instance with
			// all classifiers.
			String s = fsFilter.getEvaluatorClassPath();
			String s2 = fsFilter.getEvaluatorParamStr();
			String s3 = fsFilter.getSearchMethodClassPath();
			String s4 = fsFilter.getSearchMethodParamStr();
			logger.debug("Filtering current feature vector with the following fillter : \n{}",
					fsFilter.toString());
			DEInstances filteredInstances = fsFilter.useFilter(deInstances);
			logger.trace("Filtered Instances: \n{}", filteredInstances.getInstances());
			ClassifiersEvaluator classifierEval = new ClassifiersEvaluator();
			classifierEval.setClassifiersMap(classifiersMap);
			classifierEval.setFeatureSelectionFilterMap(null);
			classifierEval.evaluate(filteredInstances);
			ArrayList<EvaluationInfo> currentFSEvalResults = classifierEval
					.getEvaluationInfoResultList();
			for (EvaluationInfo evalInfo : currentFSEvalResults) {
				String classifierClassPath = evalInfo.getEvalParameters().getClassifier()
						.getClassPath();
				if (!evaluationInfoMap.containsKey(classifierClassPath))
					evaluationInfoMap.put(classifierClassPath, new ArrayList<EvaluationInfo>());
				evaluationInfoMap.get(classifierClassPath).add(evalInfo);
			}
		}
		updateEvaluationList();
	}
}
