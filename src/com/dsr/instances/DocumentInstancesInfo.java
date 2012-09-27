package com.dsr.instances;

import java.io.Serializable;
import java.util.Vector;
import com.dsr.util.enumu.FeatureValuesEnum;
import com.dsr.util.enumu.NGramEnum;

public class DocumentInstancesInfo implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Vector<String> featuresVec;
	private Vector<String> categoriesVec;
	private Vector<Double> featureIDFValuesVec;
	private NGramEnum nGramType;
	private FeatureValuesEnum featuresType;

	public DocumentInstancesInfo() {

	}

	public Vector<String> getFeaturesVec() {
		return featuresVec;
	}

	public void setFeaturesVec(Vector<String> featuresVec) {
		this.featuresVec = featuresVec;
	}

	public Vector<String> getCategoriesVec() {
		return categoriesVec;
	}

	public void setCategoriesVec(Vector<String> categoriesVec) {
		this.categoriesVec = categoriesVec;
	}

	public Vector<Double> getFeatureIDFValuesVec() {
		return featureIDFValuesVec;
	}

	public void setFeatureIDFValuesVec(Vector<Double> featureIDFValuesVec) {
		this.featureIDFValuesVec = featureIDFValuesVec;
	}

	public NGramEnum getNGramType() {
		return nGramType;
	}

	public void setNGramType(NGramEnum nGramType) {
		this.nGramType = nGramType;
	}

	public FeatureValuesEnum getFeaturesType() {
		return featuresType;
	}

	public void setFeaturesType(FeatureValuesEnum featuresType) {
		this.featuresType = featuresType;
	}

}
