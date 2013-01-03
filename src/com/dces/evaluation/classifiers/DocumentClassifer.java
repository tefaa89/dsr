package com.dces.evaluation.classifiers;

import java.util.Map;
import weka.classifiers.AbstractClassifier;

public class DocumentClassifer {
	private AbstractClassifier classifier;

	public DocumentClassifer() {

	}

	public String setOptions(Map<String, String> options) {
		String optionsStr = "";
		for (String option : options.keySet()) {
			String value = options.get(option) + " ";
			if (value.trim().equals("*"))
				continue;
			if (option.trim().equals("*")) {
				optionsStr += "-" + options.get(option) + " ";
			} else
				optionsStr += "-" + option + " " + options.get(option) + " ";
		}
		try {
			classifier.setOptions(weka.core.Utils.splitOptions(optionsStr));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return optionsStr;
	}

	public AbstractClassifier getClassifier() {
		return classifier;
	}

	public void setClassifier(AbstractClassifier classifier) {
		this.classifier = classifier;
	}
}
