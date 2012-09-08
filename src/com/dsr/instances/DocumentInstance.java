package com.dsr.instances;

import java.io.Serializable;
import java.util.Vector;

public class DocumentInstance implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private DocumentNGrams docNGram;
	private Vector<Integer> featuresFValues;
	private Vector<Double> featuresTFValuesVec;
	private Vector<Double> featuresTFIDFValuesVec;

	public DocumentInstance() {

	}

	public DocumentInstance(DocumentNGrams docNGram) {
		this.docNGram = docNGram;
	}

	public DocumentNGrams getDocNGram() {
		return docNGram;
	}

	public void setDocNGram(DocumentNGrams docNGram) {
		this.docNGram = docNGram;
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
		return "===> Name: " + docNGram.getName() + "\n     Category: " + docNGram.getCategory()
				+ "\n     Content: " + docNGram.getContent() + "\n     NGramType: "
				+ docNGram.getnGramType() + "\n     NGram: " + docNGram.getnGramVec()
				+ "\n     FValues: " + featuresFValues + "\n     TFValues: " + featuresTFValuesVec
				+ "\n     TF-IDFValues: " + featuresTFIDFValuesVec
				+ "\n ========================================================\n";
	}
}
