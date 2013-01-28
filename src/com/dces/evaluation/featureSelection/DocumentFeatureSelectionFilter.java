package com.dces.evaluation.featureSelection;

import java.util.Map;
import org.slf4j.LoggerFactory;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.core.Instances;
import weka.core.OptionHandler;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import ch.qos.logback.classic.Logger;
import com.dces.evaluation.DEInstances;

public class DocumentFeatureSelectionFilter {
	private static Logger logger = (Logger) LoggerFactory
			.getLogger(DocumentFeatureSelectionFilter.class);
	private AttributeSelection attFilter;
	private double attributesScalingFactor;

	public DocumentFeatureSelectionFilter() {
		attFilter = new AttributeSelection();
		attributesScalingFactor = 1;
	}

	public DocumentFeatureSelectionFilter(DocumentFeatureSelectionFilter fsF) {
		this.attFilter = fsF.getAttributeSelectionObj();
		attributesScalingFactor = 1;
	}

	public double getAttibutesScalingFactor() {
		return attributesScalingFactor;
	}

	public void setAttibutesScalingFactor(double attributesScalingFactor) {
		this.attributesScalingFactor = attributesScalingFactor;
	}

	public AttributeSelection getAttributeSelectionObj() {
		return attFilter;
	}

	public void setEvaluator(String classPath) {
		try {
			ASEvaluation eval = (ASEvaluation) Class.forName(classPath).newInstance();
			attFilter.setEvaluator(eval);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			logger.error(
					"Creating an ASEValuation object for feature evaluator from ClassPath: {}",
					e.toString());
		}
	}

	public void setSearchMethod(String classPath) {
		ASSearch search;
		try {
			search = (ASSearch) Class.forName(classPath).newInstance();
			attFilter.setSearch(search);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			logger.error("Creating an ASSearch object for a search method from ClassPath: ",
					e.toString());
		}
	}

	private void setOptions(Map<String, String> options, OptionHandler handler) {
		String optionsStr = "";
		for (String option : options.keySet()) {
			String value = options.get(option);
			if (value.equals("") && option.equals("*"))
				continue;
			if (option.equals("*")) {
				optionsStr += "-" + options.get(option) + " ";
			} else
				optionsStr += "-" + option + " " + options.get(option) + " ";
		}
		try {
			if (optionsStr.trim() != "")
				handler.setOptions(weka.core.Utils.splitOptions(optionsStr));
		} catch (Exception e) {
			logger.error("Settings Options for Feature Evaluator: {}", e.toString());
		}
	}

	public void setEvaluatorOptions(Map<String, String> options) {
		setOptions(options, (OptionHandler) attFilter.getEvaluator());
	}

	public void setSearchMethodOptions(Map<String, String> options) {
		setOptions(options, (OptionHandler) attFilter.getSearch());
	}

	public boolean hasSameFilterClass(DocumentFeatureSelectionFilter fsF) {
		return getEvaluatorClassPath().equals(fsF.getEvaluatorClassPath());
	}

	public boolean hasSameSearchMethodClass(DocumentFeatureSelectionFilter fsF) {
		return getSearchMethodClassPath().equals(fsF.getSearchMethodClassPath());
	}

	public String getEvaluatorClassPath() {
		return attFilter.getEvaluator().getClass().getName();
	}

	public String getSearchMethodClassPath() {
		return attFilter.getSearch().getClass().getName();
	}

	public String[] getEvaluatorParam() {
		return ((OptionHandler) attFilter.getEvaluator()).getOptions();
	}

	public String[] getSearchMethodParam() {
		return ((OptionHandler) attFilter.getSearch()).getOptions();
	}

	public String getEvaluatorParamStr() {
		String[] optionsArr = getEvaluatorParam();
		String res = "";
		for (String str : optionsArr)
			res += str + " ";
		return res.trim();
	}

	public String getSearchMethodParamStr() {
		String[] optionsArr = getSearchMethodParam();
		String res = "";
		for (String str : optionsArr)
			res += str + " ";
		return res.trim();
	}

	public DEInstances useFilter(DEInstances instances) {
		DEInstances filteredInstances = new DEInstances();
		if (attFilter.getEvaluator() == null)
			return instances;
		try {
			Instances unFilteredInstances = instances.getInstances();
			attFilter.setInputFormat(unFilteredInstances);
			Instances filteredInstancesWeka = Filter.useFilter(unFilteredInstances, attFilter);
			cutAttributes(filteredInstancesWeka);
			filteredInstances.setInstances(filteredInstancesWeka);
			filteredInstances.setParameters(instances.getEvaluationParameters());
			filteredInstances.getEvaluationParameters().setFeatureSelection(this);
		} catch (Exception e) {
			logger.error("Applying filter on instances: {}", e.toString());
		}
		return filteredInstances;
	}

	private void cutAttributes(Instances instances) {
		int attrNum = instances.numAttributes();
		if (attributesScalingFactor >= 1 || attrNum <= 2)
			return;
		// This -1 is to exclude the class attribute
		int numAttrToKeep = (int) ((attrNum - 1) * attributesScalingFactor);
		int numAttrToCut = (attrNum - 1) - numAttrToKeep;
		// Keep deleting the attribute before class attribute "numAttrToCut"
		// times.
		for (; numAttrToCut >= 0; numAttrToCut--) {
			instances.deleteAttributeAt(instances.numAttributes() - 2);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DocumentFeatureSelectionFilter))
			return false;
		DocumentFeatureSelectionFilter fsF = (DocumentFeatureSelectionFilter) obj;
		if (!hasSameFilterClass(fsF) || !hasSameSearchMethodClass(fsF))
			return false;
		String[] optionsEvaluatorArr1 = fsF.getEvaluatorParam();
		String[] optionsEvaluatorArr2 = getEvaluatorParam();
		if (optionsEvaluatorArr1.length != optionsEvaluatorArr2.length)
			return false;
		for (int i = 0; i < optionsEvaluatorArr1.length; i++)
			if (!optionsEvaluatorArr1[i].equals(optionsEvaluatorArr2[i]))
				return false;
		String[] optionsSearchArr1 = fsF.getSearchMethodParam();
		String[] optionsSearchArr2 = getSearchMethodParam();
		if (optionsSearchArr1.length != optionsSearchArr2.length)
			return false;
		for (int i = 0; i < optionsSearchArr1.length; i++)
			if (!optionsSearchArr1[i].equals(optionsSearchArr2[i]))
				return false;
		return true;
	}

	public String toString() {
		return "Selection Method: " + getEvaluatorClassPath() + "\nSelection Param: "
				+ getEvaluatorParamStr();
	}
}
