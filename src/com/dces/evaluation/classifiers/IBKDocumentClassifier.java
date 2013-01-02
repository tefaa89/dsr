package com.dces.evaluation.classifiers;

import java.util.Map;
import weka.classifiers.lazy.IBk;

public class IBKDocumentClassifier extends DocumentClassifer {

	public IBKDocumentClassifier() {
		setClassifier(new IBk());
	}

	@Override
	public String setOptions(Map<String, String> options) {
		return setOptions(options, (IBk) getClassifier());
	}

}
