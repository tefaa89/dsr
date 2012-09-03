package com.dsr.instances;

import java.util.Iterator;
import java.util.Vector;

public class DocumentInstances implements Iterable<DocumentInstance>{
	private Vector<DocumentInstance> docInstanceVec;
	private Vector<String> features;
	private Vector<Double> featuresIDFVec;
	private Vector<String> categoriesVec;
	
	public DocumentInstances(Vector<DocumentInstance> docInstanceVec) {
		this.docInstanceVec = docInstanceVec;
	}
	
	public Vector<DocumentInstance> getDocInstanceVec() {
		return docInstanceVec;
	}
	
	public void setDocInstanceVec(Vector<DocumentInstance> docInstanceVec) {
		this.docInstanceVec = docInstanceVec;
	}

	public Vector<String> getFeatures() {
		return features;
	}

	public void setFeatures(Vector<String> features) {
		this.features = features;
	}

	public Vector<Double> getFeaturesIDFVec() {
		return featuresIDFVec;
	}

	public void setFeaturesIDFVec(Vector<Double> featuresIDFVec) {
		this.featuresIDFVec = featuresIDFVec;
	}
	
	public Vector<String> getCategoriesVec() {
		return categoriesVec;
	}

	public void setCategoriesVec(Vector<String> categoriesVec) {
		this.categoriesVec = categoriesVec;
	}

	public String toString()
	{
		return docInstanceVec.toString();
	}

	@Override
	public Iterator<DocumentInstance> iterator() {
		return docInstanceVec.iterator();
	}
}
