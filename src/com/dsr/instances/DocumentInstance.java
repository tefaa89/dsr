package com.dsr.instances;

import java.util.Vector;

public class DocumentInstance extends DocumentNGrams {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<Integer> featuresFValues;
	private Vector<Double> featuresTFValuesVec;
	private Vector<Double> featuresTFIDFValuesVec;

	public DocumentInstance(DocumentNGrams docNGram) {
		super(docNGram, docNGram.getnGramVec(), docNGram.getnGramType());
	}

	public Vector<Integer> getFeaturesFValues() {
		return featuresFValues;
	}

	public void setFeaturesFValues(Vector<Integer> featuresFValues) {
		this.featuresFValues = featuresFValues;
	}

	public Vector<Double> getFeaturesTFValues() {
		return featuresTFValuesVec;
	}

	public void setFeaturesTFValues(Vector<Double> featuresTFValuesVec) {
		this.featuresTFValuesVec = featuresTFValuesVec;
	}

	public Vector<Double> getFeaturesTFIDFValuesVec() {
		return featuresTFIDFValuesVec;
	}

	public void setFeaturesTFIDFValuesVec(Vector<Double> featuresTFIDFValuesVec) {
		this.featuresTFIDFValuesVec = featuresTFIDFValuesVec;
	}

	@Override
	public String toString() {
		return "===> Name: " + getName() + "\n     Category: " + getCategory() + "\n     Content: "
				+ getContent() + "\n     NGramType: " + getnGramType() + "\n     NGram: "
				+ getnGramVec() + "\n     FValues: " + featuresFValues + "\n     TFValues: "
				+ featuresTFValuesVec + "\n     TF-IDFValues: " + featuresTFIDFValuesVec
				+ "\n ========================================================\n";
	}
}
