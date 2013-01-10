package com.dces.evaluation.classifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.slf4j.LoggerFactory;
import weka.classifiers.Evaluation;
import ch.qos.logback.classic.Logger;
import com.dces.evaluation.DEInstances;
import com.dces.evaluation.EvaluationInfo;
import com.dces.evaluation.EvaluationResults;
import com.dces.evaluation.EvaluatorAbstract;
import com.dces.evaluation.featureSelection.DocumentFeatureSelectionFilter;

public class ClassifiersEvaluator extends EvaluatorAbstract {
	private static Logger logger = (Logger) LoggerFactory.getLogger(ClassifiersEvaluator.class);
	/**
	 * featureSelectionFilterMap holds the best document feature selection
	 * filter for every classifier string path
	 */
	private Map<String, DocumentFeatureSelectionFilter> featureSelectionFilterMap;
	private Map<String, ArrayList<DocumentClassifer>> classifiersMap;

	public ClassifiersEvaluator() {
		classifiersMap = new HashMap<String, ArrayList<DocumentClassifer>>();
		featureSelectionFilterMap = new HashMap<String, DocumentFeatureSelectionFilter>();
	}

	public void setClassifiersMap(Map<String, ArrayList<DocumentClassifer>> classifiersMap) {
		this.classifiersMap = classifiersMap;
	}

	public void setFeatureSelectionFilterMap(Map<String, DocumentFeatureSelectionFilter> fsMap) {
		this.featureSelectionFilterMap = fsMap;
	}

	public void evaluate(DEInstances deInstances) {
		DocumentFeatureSelectionFilter fsFilter = null;
		for (String key : classifiersMap.keySet()) {
			ArrayList<DocumentClassifer> currentClassifiersList = classifiersMap.get(key);
			if (featureSelectionFilterMap != null)
				fsFilter = featureSelectionFilterMap.get(key);

			DEInstances filteredInstances = (fsFilter == null) ? deInstances : fsFilter
					.useFilter(deInstances);
			for (DocumentClassifer classifier : currentClassifiersList) {
				try {
					Evaluation eval = new Evaluation(filteredInstances.getInstances());
					eval.crossValidateModel(classifier.getClassifier(),
							filteredInstances.getInstances(), 10, new Random(1));
					EvaluationResults evalResults = new EvaluationResults();
					evalResults.setEvaluation(eval);
					
					EvaluationInfo evalInfo = new EvaluationInfo();
					evalInfo.setEvalParameters(filteredInstances.getEvaluationParameters());
					evalInfo.setEvalResults(evalResults);
					evalInfo.getEvalParameters().setClassifier(classifier);
					updateEvaluationInfo(evalInfo);
				} catch (Exception e) {
					logger.error("Evaluating using crossValidation: {}", e.toString());
				}
			}
		}
	}
}