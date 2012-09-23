package com.dsr.instances;

import java.io.Serializable;
import java.util.Vector;

public class DocumentInstance implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private DocumentNGrams docNGram;
	private Vector<Double> featuresValuesVec;

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

	public Vector<Double> getFeaturesValues() {
		return featuresValuesVec;
	}

	public void setFeaturesValues(Vector<Double> featuresValuesVec) {
		this.featuresValuesVec = featuresValuesVec;
	}

	@Override
	public String toString() {
		return "===> Name: " + docNGram.getName() + "\n     Category: " + docNGram.getCategory()
				+ "\n     Content: " + docNGram.getContent() + "\n     NGramType: "
				+ docNGram.getnGramType() + "\n     NGram: " + docNGram.getnGramVec()
				+ "\n     FValues: " + featuresValuesVec
				+ "\n ========================================================\n";
	}
}
