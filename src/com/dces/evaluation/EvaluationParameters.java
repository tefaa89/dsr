package com.dces.evaluation;

import java.util.ArrayList;
import com.dces.evaluation.classifiers.DocumentClassifer;
import com.dces.evaluation.featureSelection.DocumentFeatureSelectionFilter;
import com.dces.util.enumu.FeaturesModelEnum;

public class EvaluationParameters {
	private transient DocumentClassifer classifier;
	private transient DocumentFeatureSelectionFilter featureSelection;
	private String classifierNameStr;
	private String classifierParamStr;
	private String fsEvaluatorNameStr;
	private String fsEvaluatorParamStr;
	private String fsSearchMethodNameStr;
	private String fsSearchMethodParamStr;
	private String numOfAttributesStr;

	private FeaturesModelEnum featureModelEnum;
	private transient ArrayList<String> selectedAttributes;
	private boolean tfBool;
	private boolean idfBool;
	private boolean useStopList;
	private boolean useStemming;

	public EvaluationParameters() {
	}

	public EvaluationParameters(EvaluationParameters evalParams) {
		setClassifier(evalParams.getClassifier());
		setSelectedAttributes(evalParams.getSelectedAttributes());
		setFeatureSelection(evalParams.getFeatureSelection());
		setFeatureModelEnum(featureModelEnum = evalParams.getFeatureModelEnum());
		this.tfBool = evalParams.isTfBool();
		this.idfBool = evalParams.isIdfBool();
		this.useStemming = evalParams.isUseStemming();
		this.useStopList = evalParams.isUseStopList();
	}

	public void setSelectedAttributes(ArrayList<String> selectedAttributes) {
		this.selectedAttributes = selectedAttributes;
		if(selectedAttributes != null)
			numOfAttributesStr = selectedAttributes.size() + "";
	}

	public ArrayList<String> getSelectedAttributes() {
		return selectedAttributes;
	}

	public DocumentClassifer getClassifier() {
		return classifier;
	}

	public void setClassifier(DocumentClassifer classifier) {
		this.classifier = classifier;
		if (classifier != null) {
			classifierNameStr = classifier.getClassPath();
			classifierParamStr = classifier.getOptionsStr();
		}
	}

	public DocumentFeatureSelectionFilter getFeatureSelection() {
		return featureSelection;
	}

	public void setFeatureSelection(DocumentFeatureSelectionFilter featureSelection) {
		this.featureSelection = featureSelection;
		if (featureSelection != null) {
			fsEvaluatorNameStr = featureSelection.getEvaluatorClassPath();
			fsEvaluatorParamStr = featureSelection.getEvaluatorParamStr();
			fsSearchMethodNameStr = featureSelection.getSearchMethodClassPath();
			fsSearchMethodParamStr = featureSelection.getSearchMethodParamStr();
		}
	}

	public FeaturesModelEnum getFeatureModelEnum() {
		return featureModelEnum;
	}

	public void setFeatureModelEnum(FeaturesModelEnum featureModelEnum) {
		this.featureModelEnum = featureModelEnum;
	}

	public boolean isTfBool() {
		return tfBool;
	}

	public void setTfBool(boolean tfBool) {
		this.tfBool = tfBool;
	}

	public boolean isIdfBool() {
		return idfBool;
	}

	public void setIdfBool(boolean idfBool) {
		this.idfBool = idfBool;
	}

	public boolean isUseStopList() {
		return useStopList;
	}

	public void setUseStopList(boolean useStopList) {
		this.useStopList = useStopList;
	}

	public boolean isUseStemming() {
		return useStemming;
	}

	public void setUseStemming(boolean useStemming) {
		this.useStemming = useStemming;
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

	public String getClassifierNameStr() {
		return classifierNameStr;
	}

	public String getClassifierParamStr() {
		return classifierParamStr;
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

	@Override
	public String toString() {
		String res = "# Evaluation Parameters:\n\t";
		res += "Classifier Class: " + classifier.getClassPath();
		res += "\n\t";
		res += "Classifier Parameters: " + classifier.getOptionsStr();
		res += "\n\t";
		res += "Feature Space Model: "
				+ (featureModelEnum == null ? "None" : featureModelEnum.toString());
		res += "\n\t";
		res += "FS Evaluator Class: " + featureSelection.getEvaluatorClassPath();
		res += "\n\t";
		res += "FS Evaluator Parameters: " + featureSelection.getEvaluatorParamStr();
		res += "\n\t";
		res += "FS SearchMethod Class: " + featureSelection.getSearchMethodClassPath();
		res += "\n\t";
		res += "FS SearchMethod Parameters: " + featureSelection.getSearchMethodParamStr();
		res += "\n\t";
		res += "Num. of Selected Attributes: " + selectedAttributes.size();
		res += "\n\t";
		res += "Selected Attributes: " + selectedAttributesStr();
		res += "\n\t";
		res += "Stemming: " + useStemming;
		res += "\n\t";
		res += "StopList: " + useStopList;
		return res;
	}
}
