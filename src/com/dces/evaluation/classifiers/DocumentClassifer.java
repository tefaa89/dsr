package com.dces.evaluation.classifiers;

import java.util.Map;
import weka.classifiers.Classifier;
import weka.core.OptionHandler;

public abstract class DocumentClassifer {
	private Classifier classifier;

	public abstract String setOptions(Map<String, String> options);

	protected String setOptions(Map<String, String> options, OptionHandler optionsInterface) {
		String optionsStr = "";
		for (String option : options.keySet()) {
			if (option.trim().equals("*")) {
				String value = options.get(option) + " ";
				if (!value.trim().equals("*"))
					optionsStr += "-" + options.get(option) + " ";
			} else
				optionsStr += "-" + option + " " + options.get(option) + " ";
		}
		try {
			optionsInterface.setOptions(weka.core.Utils.splitOptions(optionsStr));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return optionsStr;
	}

	public Classifier getClassifier() {
		return classifier;
	}

	public void setClassifier(Classifier classifier) {
		this.classifier = classifier;
	}
}
