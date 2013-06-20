package com.esda.evaluation;

import java.util.ArrayList;
import com.esda.evaluation.classifiers.ClassificationAlgorithm;
import com.esda.evaluation.featureExtraction.FeatureExtractorFilter;
import com.esda.evaluation.featureSelection.FeatureSelectionFilter;

public class EvaluationParameters {
	private transient ClassificationAlgorithm classifier;
	private transient FeatureSelectionFilter featureSelector;
	private transient FeatureExtractorFilter featureExtractor;
	private String classifierNameStr;
	private String classifierParamStr;
	private String fsEvaluatorNameStr;
	private String fsEvaluatorParamStr;
	private String feNameStr;
	private String feParamStr;

	private String fsSearchMethodNameStr;
	private String fsSearchMethodParamStr;
	private String numOfAttributesStr;

	private ArrayList<String> selectedAttributes;

	public EvaluationParameters() {
	}

	public EvaluationParameters(EvaluationParameters evalParams) {
		setClassifier(evalParams.getClassifier());
		setSelectedAttributes(evalParams.getSelectedAttributes());
		setFeatureSelection(evalParams.getFeatureSelection());
		setFeatureExtractor(evalParams.getFeatureExtractor());
	}

	public ClassificationAlgorithm getClassifier() {
		return classifier;
	}

	public String getClassifierNameStr() {
		return classifierNameStr;
	}

	public String getClassifierParamStr() {
		return classifierParamStr;
	}

	public FeatureExtractorFilter getFeatureExtractor() {
		return featureExtractor;
	}

	public FeatureSelectionFilter getFeatureSelection() {
		return featureSelector;
	}

	public String getFExtractionNameStr() {
		return feNameStr;
	}

	public String getFExtractionParamStr() {
		return feParamStr;
	}

	public String getFsEvaluatorNameStr() {
		return fsEvaluatorNameStr;
	}

	public String getFsEvaluatorParamStr() {
		return fsEvaluatorParamStr;
	}

	public String getFsSearchMethodNameStr() {
		return fsSearchMethodNameStr;
	}

	public String getFsSearchMethodParamStr() {
		return fsSearchMethodParamStr;
	}

	public String getNumOfAttributesStr() {
		return numOfAttributesStr;
	}

	public ArrayList<String> getSelectedAttributes() {
		return selectedAttributes;
	}

	private String selectedAttributesStr() {
		String res = "{";
		for (String att : selectedAttributes) {
			if (!att.toLowerCase().equals("class"))
				res += att + ", ";
		}
		res = res.substring(0, res.length() - 2);
		res += "}";
		return res;
	}

	public void setClassifier(ClassificationAlgorithm classifier) {
		this.classifier = classifier;
		if (classifier != null) {
			classifierNameStr = classifier.getClassPath();
			classifierParamStr = classifier.getOptionsStr();
		}
	}

	public void setFeatureExtractor(FeatureExtractorFilter feFilter) {
		this.featureExtractor = feFilter;
		if (featureExtractor != null) {

		}
	}

	public void setFeatureSelection(FeatureSelectionFilter featureSelection) {
		this.featureSelector = featureSelection;
		if (featureSelection != null) {
			fsEvaluatorNameStr = featureSelection.getEvaluatorClassPath();
			fsEvaluatorParamStr = featureSelection.getEvaluatorParamStr();
			fsSearchMethodNameStr = featureSelection.getSearchMethodClassPath();
			fsSearchMethodParamStr = featureSelection.getSearchMethodParamStr();
		}
	}

	public void setFExtractionNameStr(String feNameStr) {
		this.feNameStr = feNameStr;
	}

	public void setFExtractionParamStr(String feParamStr) {
		this.feParamStr = feParamStr;
	}

	public void setSelectedAttributes(ArrayList<String> selectedAttributes) {
		this.selectedAttributes = selectedAttributes;
		if (selectedAttributes != null)
			numOfAttributesStr = selectedAttributes.size() + "";
	}

	@Override
	public String toString() {
		String res = "# Evaluation Parameters:\n\t";
		res += "Classifier Class: " + classifierNameStr;
		res += "\n\t";
		res += "Classifier Parameters: " + classifierParamStr;
		res += "\n\t";
		res += "FS Evaluator Class: " + fsEvaluatorNameStr;
		res += "\n\t";
		res += "FS Evaluator Parameters: " + fsEvaluatorParamStr;
		res += "\n\t";
		res += "FS SearchMethod Class: " + fsSearchMethodNameStr;
		res += "\n\t";
		res += "FS SearchMethod Parameters: " + fsSearchMethodParamStr;
		res += "\n\t";
		res += "Num. of Selected Attributes: " + selectedAttributes.size();
		res += "\n\t";
		res += "Selected Attributes: " + selectedAttributesStr();
		res += "\n\t";
		res += "Feature Extractor Class: " + feNameStr;
		res += "\n\t";
		res += "Feature Extractor Parameters: " + feParamStr;
		return res;
	}
}
