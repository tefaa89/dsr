package com.esda.evaluation.featureSelection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import com.esda.evaluation.DEInstances;
import com.esda.evaluation.EvaluationInfo;
import com.esda.evaluation.EvaluatorAbstract;
import com.esda.evaluation.classifiers.ClassificationAlgorithm;
import com.esda.evaluation.classifiers.ClassifiersEvaluator;

public class FeatureSelectionEvaluator extends EvaluatorAbstract {
	private static Logger logger = (Logger) LoggerFactory
			.getLogger(FeatureSelectionEvaluator.class);
	/**
	 * evaluationInfoMap holds the evaluation list for every classifier string
	 * path. Every filtered feature vector will be evaluated on all classifiers
	 * and then stored in the evaluationInfoMap.
	 */
	private Map<String, ArrayList<EvaluationInfo>> evaluationInfoMap;
	private ArrayList<FeatureSelectionFilter> featureSelectionFilterList;
	private ArrayList<ClassificationAlgorithm> classifierList;

	public FeatureSelectionEvaluator() {
		evaluationInfoMap = new HashMap<String, ArrayList<EvaluationInfo>>();
	}

	public ArrayList<FeatureSelectionFilter> getFeatureSelectionFilterList() {
		return featureSelectionFilterList;
	}

	public Map<String, FeatureSelectionFilter> getBestClassifiersFSMap() {
		// The evaluationInfoList in the parent class contains the best
		// evaluations only
		Map<String, FeatureSelectionFilter> map = new HashMap<String, FeatureSelectionFilter>();
		for (EvaluationInfo evalInfo : evaluationInfoResultList) {
			String classifierClassPath = evalInfo.getEvalParameters().getClassifier()
					.getClassPath();
			FeatureSelectionFilter fsF = evalInfo.getEvalParameters().getFeatureSelection();
			map.put(classifierClassPath, fsF);
		}
		return map;
	}

	public void setFeatureSelectionFilterList(
			ArrayList<FeatureSelectionFilter> featureSelectionFilterList) {
		this.featureSelectionFilterList = featureSelectionFilterList;
	}

	public void setClassifierList(ArrayList<ClassificationAlgorithm> classifierList) {
		logger.trace("Feature Selecton Evaluator Classifiers are {}", classifierList);
		this.classifierList = classifierList;
	}

	private Map<String, ArrayList<ClassificationAlgorithm>> getClassifiersMap() {
		Map<String, ArrayList<ClassificationAlgorithm>> classifiersMap = new HashMap<String, ArrayList<ClassificationAlgorithm>>();
		for (ClassificationAlgorithm classifier : classifierList) {
			String key = classifier.getClassPath();
			if (!classifiersMap.containsKey(key))
				classifiersMap.put(key, new ArrayList<ClassificationAlgorithm>());
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
		Map<String, ArrayList<ClassificationAlgorithm>> classifiersMap = getClassifiersMap();
		for (FeatureSelectionFilter fsFilter : featureSelectionFilterList) {
			// Apply filter on deInstances and then evaluate the instance with
			// all classifiers.
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
