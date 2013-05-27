package com.esda.evaluation.classifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.slf4j.LoggerFactory;
import weka.classifiers.Evaluation;
import ch.qos.logback.classic.Logger;
import com.esda.evaluation.DEInstances;
import com.esda.evaluation.EvaluationInfo;
import com.esda.evaluation.EvaluationParameters;
import com.esda.evaluation.EvaluationResults;
import com.esda.evaluation.EvaluatorAbstract;
import com.esda.evaluation.featureSelection.FeatureSelectionFilter;
import com.websocket.ESWebSocket;

public class ClassifiersEvaluator extends EvaluatorAbstract {
	private static Logger logger = (Logger) LoggerFactory.getLogger(ClassifiersEvaluator.class);
	/**
	 * featureSelectionFilterMap holds the best document feature selection
	 * filter for every classifier string path
	 */
	private Map<String, FeatureSelectionFilter> featureSelectionFilterMap;
	private Map<String, ArrayList<ClassificationAlgorithm>> classifiersMap;

	public ClassifiersEvaluator() {
		classifiersMap = new HashMap<String, ArrayList<ClassificationAlgorithm>>();
		featureSelectionFilterMap = new HashMap<String, FeatureSelectionFilter>();
	}

	public void setClassifiersMap(Map<String, ArrayList<ClassificationAlgorithm>> classifiersMap) {
		this.classifiersMap = classifiersMap;
	}

	public void setFeatureSelectionFilterMap(Map<String, FeatureSelectionFilter> fsMap) {
		this.featureSelectionFilterMap = fsMap;
	}

	@Override
	public void clear()
	{
		super.clear();
		featureSelectionFilterMap = new HashMap<String, FeatureSelectionFilter>();
	}

	public void evaluate(DEInstances deInstances) {
		FeatureSelectionFilter fsFilter = null;
		for (String key : classifiersMap.keySet()) {
			ArrayList<ClassificationAlgorithm> currentClassifiersList = classifiersMap.get(key);
			if (featureSelectionFilterMap != null)
				fsFilter = featureSelectionFilterMap.get(key);

			System.out.println("###### NUMBER OF INSTANCES: " + Thread.currentThread().getName() + " "+ deInstances.getInstances().numInstances());
			DEInstances filteredInstances = (fsFilter == null) ? deInstances : fsFilter
					.useFilter(deInstances);
			for (ClassificationAlgorithm classifier : currentClassifiersList) {
				try {
					logger.info("Evaluating classifier :\n" + classifier);
					Evaluation eval = new Evaluation(filteredInstances.getInstances());
					eval.crossValidateModel(classifier.getClassifier(),
							filteredInstances.getInstances(), 10, new Random(1));
					EvaluationResults evalResults = new EvaluationResults();
					EvaluationParameters evalParams = new EvaluationParameters(
							filteredInstances.getEvaluationParameters());
					evalParams.setSelectedAttributes(filteredInstances.getAttributesList());
					evalResults.setEvaluation(eval);

					EvaluationInfo evalInfo = new EvaluationInfo();
					evalInfo.setEvalParameters(evalParams);
					evalInfo.setEvalResults(evalResults);
					evalInfo.getEvalParameters().setClassifier(classifier);
					ESWebSocket.updateClassifierAcc(evalInfo);
					updateEvaluationInfo(evalInfo);
				} catch (Exception e) {
					logger.error("Evaluating using crossValidation: {}", e.toString());
				}
			}
		}
	}
}
