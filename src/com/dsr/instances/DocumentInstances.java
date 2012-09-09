package com.dsr.instances;

import java.util.Iterator;
import java.util.Vector;

public class DocumentInstances implements Iterable<DocumentInstance>{
	private Vector<DocumentInstance> docInstanceVec;

	private DocumentInstancesInfo docInstancesInfo;

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
		return docInstancesInfo.getFeaturesVec();
	}

	public void setFeatures(Vector<String> features) {
		this.docInstancesInfo.setFeaturesVec(features);
	}

	public Vector<Double> getFeaturesIDFVec() {
		return docInstancesInfo.getFeatureIDFValuesVec();
	}

	public void setFeaturesIDFVec(Vector<Double> featuresIDFVec) {
		this.docInstancesInfo.setFeatureIDFValuesVec(featuresIDFVec);
	}

	public Vector<String> getCategoriesVec() {
		return docInstancesInfo.getCategoriesVec();
	}

	public void setCategoriesVec(Vector<String> categoriesVec) {
		this.docInstancesInfo.setCategoriesVec(categoriesVec);
	}

	public DocumentInstancesInfo getDocInstancesInfo() {
		return docInstancesInfo;
	}

	public void setDocInstancesInfo(DocumentInstancesInfo docInstancesInfo) {
		this.docInstancesInfo = docInstancesInfo;
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
