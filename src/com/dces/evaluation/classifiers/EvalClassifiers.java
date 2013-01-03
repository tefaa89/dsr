package com.dces.evaluation.classifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Evaluation;
import com.dces.configuration.Config;
import com.dces.evaluation.DEInstances;
import com.dces.util.ClassifierInfoXML;

public class EvalClassifiers {
	private DEInstances deInstances;
	private ArrayList<ClassifierInfoXML> classifierInfoList;
	/**
	 * This list includes all possible options for the current
	 * classifierInfoXml.
	 */
	private ArrayList<Map<String, String>> currentClassifierOptionsList;
	private DocumentClassifer currentDocumentClassifier;
	private int classifierInfoState;
	private int currentClassifierOptionsState;

	public EvalClassifiers(DEInstances deInstances) {
		this.deInstances = deInstances;
		classifierInfoList = Config.getClassifiersInfo();
		reSetState();
		initCurrentState();
	}

	private void initCurrentState() {
		initCurrentDocClassifier();
		initDocClassifiersList();
	}

	private void initCurrentDocClassifier() {
		try {
			currentDocumentClassifier = new DocumentClassifer();
			currentDocumentClassifier.setClassifier((AbstractClassifier) Class.forName(
					classifierInfoList.get(classifierInfoState).getClassName()).newInstance());
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void initDocClassifiersList() {
		currentClassifierOptionsList = new ArrayList<Map<String, String>>();
		ClassifierInfoXML currentClassifierInfo = classifierInfoList.get(classifierInfoState);
		// All possible current classifier parameter combination count
		Map<String, String[]> parametersMap = currentClassifierInfo.getParameters();
		int numOfParamters = parametersMap.size();
		String[] keys = parametersMap.keySet().toArray(new String[0]);
		int[] indexArr = new int[numOfParamters];

		while (true) {
			Map<String, String> currentOptions = new HashMap<String, String>();
			for (int i = 0; i < indexArr.length; i++)
				currentOptions.put(keys[i], parametersMap.get(keys[i])[indexArr[i]]);
			currentClassifierOptionsList.add(currentOptions);
			for (int i = indexArr.length - 1; i >= 0; i--) {
				indexArr[i]++;
				if (indexArr[i] < parametersMap.get(keys[i]).length)
					break;
				if (i != 0)
					indexArr[i] = 0;
			}
			if (indexArr[0] >= parametersMap.get(keys[0]).length)
				break;
		}
	}

	public DEInstances getNext() {
		if (!hasNext())
			return null;
		nextState();
		initCurrentState();
		try {
			String optionStr = currentDocumentClassifier.setOptions(currentClassifierOptionsList
					.get(currentClassifierOptionsState));
			Evaluation eval = new Evaluation(deInstances.getInstances());
			eval.crossValidateModel(currentDocumentClassifier.getClassifier(),
					deInstances.getInstances(), 10, new Random(1));
			// Set Classifier Evaluation Parameters
			String classPath = classifierInfoList.get(classifierInfoState).getClassName();
			deInstances.getEvaluationParameters().setClassifierClassPath(classPath);
			deInstances.getEvaluationParameters().setParametersStr(optionStr);

			// Set Classifier Evaluation Results
			deInstances.getEvaluationResults().setConfusionMatrix(eval.confusionMatrix());
			deInstances.getEvaluationResults().setSummaryString(eval.toSummaryString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return deInstances;
	}

	private void nextState() {
		currentClassifierOptionsState++;
		if (currentClassifierOptionsState >= currentClassifierOptionsList.size()) {
			currentClassifierOptionsState = 0;
			classifierInfoState++;
		}
	}

	public boolean hasNext() {
		return currentClassifierOptionsState + 1 >= currentClassifierOptionsList.size()
				&& classifierInfoState + 1 >= classifierInfoList.size() ? false : true;
	}

	public void reSetState() {
		currentClassifierOptionsState = -1;
		classifierInfoState = 0;
	}
}
