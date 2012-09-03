package com.dsr.instances;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Vector;

public class DocumentFeaturesBuilder {

	private Vector<DocumentNGrams> docNGramVec;
	private Vector<String> featuresVec;
	
	public DocumentFeaturesBuilder(Vector<DocumentNGrams> docNGramVec) {
		this.docNGramVec = docNGramVec;
	}
	
	public void buildFeatures() {
		combineFeatures();
		removeDuplicateFeatures();
		Collections.sort(featuresVec);
	}
	
	private void combineFeatures()
	{
		Vector<String> res = new Vector<String>();
		for(DocumentNGrams nGram:docNGramVec)
			res.addAll(nGram.getnGramVec());
		featuresVec = res;
	}
	
	private void removeDuplicateFeatures()
	{
		featuresVec = new Vector<String>(new LinkedHashSet<String>(featuresVec));
	}
	
	public Vector<String> getFeaturesVec() {
		return featuresVec;
	}
}
