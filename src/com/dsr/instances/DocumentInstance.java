package com.dsr.instances;

import java.io.Serializable;
import java.util.Vector;

public class DocumentInstance implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private DocumentNGrams docNGram;
	private boolean effective;
	private Vector<Double> featuresValuesVec;

	public DocumentInstance() {
		effective = false;
		docNGram = new DocumentNGrams();
		featuresValuesVec = new Vector<Double>();
	}

	public DocumentInstance(DocumentNGrams docNGram) {
		this();
		this.docNGram = docNGram;
	}

	public DocumentNGrams getDocNGram() {
		return docNGram;
	}

	public void setDocNGram(DocumentNGrams docNGram) {
		this.docNGram = docNGram;
	}

	public boolean isEffective() {
		return effective;
	}

	public void setEffective(boolean effective) {
		this.effective = effective;
	}

	public Vector<Double> getFeaturesValues() {
		return featuresValuesVec;
	}

	public void setFeaturesValues(Vector<Double> featuresValuesVec) {
		this.featuresValuesVec = featuresValuesVec;
	}

	@Override
	public String toString() {
		return "===> Name: " + docNGram.getName() + "\n     Category: " + docNGram.getCategory()
				+ "\n     Content Size: " + docNGram.getContent().length() + "\n     NGramType: "
				+ docNGram.getnGramType() + "\n     NGram: " + docNGram.getnGramVec()
				+ "\n     FValues: " + featuresValuesVec
				+ "\n ========================================================\n";
	}
}
