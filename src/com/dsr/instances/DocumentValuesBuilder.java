package com.dsr.instances;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import com.dsr.util.enumu.FeatureValuesEnum;

public class DocumentValuesBuilder {

	private Vector<String> features;

	private Vector<DocumentNGrams> docNGramVec;

	private Vector<DocumentInstance> docInstancesVec;

	private Vector<Double> featuresIDFValuesVec;

	private FeatureValuesEnum featuresType;

	public DocumentValuesBuilder(Vector<String> features, FeatureValuesEnum featuresType,
			Vector<DocumentNGrams> docNGramVec) {
		this.features = features;
		this.featuresType = featuresType;
		this.docNGramVec = docNGramVec;
	}

	public void build() {
		switch (featuresType) {
		case F_VALUES:
			initInstanceFValues(false);
			break;
		case TF_VALUES:
			initInstanceFValues(true);
			break;
		case TFIDF_VALUES:
			initInstanceFValues(true);
			buildIDFValuesVec();
			buildTFIDFVec();
			break;
		default:
			break;
		}
	}

	private void initInstanceFValues(boolean calculateTF) {
		docInstancesVec = new Vector<DocumentInstance>();
		for (DocumentNGrams docNGram : docNGramVec) {
			DocumentInstance docInstance = new DocumentInstance(docNGram);
			Vector<Double> docValues = calculateFValues(docNGram.getnGramVec());
			if(calculateTF)
				docValues = calculateTFValues(docValues);
			docInstance.setFeaturesValues(docValues);
			docInstancesVec.add(docInstance);
		}
	}

	private void buildIDFValuesVec() {
		featuresIDFValuesVec = new Vector<Double>();
		for (int i = 0; i < features.size(); i++) {
			String term = features.get(i);
			int totalDocNum = docInstancesVec.size();
			int numOfDocumentsInculudesTerm = getNumOfDocumentsContainingTerm(term);
			Double idfValue = (totalDocNum + 1) / (numOfDocumentsInculudesTerm + Double.MIN_VALUE);
			idfValue = Math.log10(idfValue);
			featuresIDFValuesVec.add(idfValue);
		}
	}

	private void buildTFIDFVec() {
		for (DocumentInstance docInstance : docInstancesVec) {
			Vector<Double> currentTFVec = docInstance.getFeaturesValues();
			docInstance.setFeaturesValues(multiplyTF_IDF(currentTFVec));
		}
	}

	private Vector<Double> calculateFValues(Vector<String> nGramVec) {
		Map<String, Integer> wordFreqMap = new HashMap<String, Integer>();
		for (String word : nGramVec) {
			Integer count = wordFreqMap.get(word);
			wordFreqMap.put(word, (count == null) ? 1 : count + 1);
		}
		Vector<Double> wordFreqVec = new Vector<Double>();
		for (String word : features) {
			if (wordFreqMap.containsKey(word))
				wordFreqVec.add((double) wordFreqMap.get(word));
			else
				wordFreqVec.add(0.0);
		}
		return wordFreqVec;
	}

	private Vector<Double> calculateTFValues(Vector<Double> fValues) {
		Vector<Double> tfValues = new Vector<Double>();
		// Iterate on fValues and calculate squrt of each value;
		Double maxFreq = Collections.max(fValues);
		for (Double fValue : fValues)
			tfValues.add(fValue / (maxFreq + Double.MIN_VALUE));
		return tfValues;
	}

	private int getNumOfDocumentsContainingTerm(String term) {
		int res = 0;
		int termIndex = features.indexOf(term);
		for (DocumentInstance docInstance : docInstancesVec)
			res += docInstance.getFeaturesValues().get(termIndex) > 0 ? 1 : 0;
		return res;
	}

	private Vector<Double> multiplyTF_IDF(Vector<Double> tfVec) {
		Vector<Double> res = new Vector<Double>();
		for (int i = 0; i < featuresIDFValuesVec.size(); i++) {
			double tfValue = tfVec.get(i);
			double idfValue = featuresIDFValuesVec.get(i);
			res.add(tfValue * idfValue);
		}
		return res;
	}

	public Vector<DocumentInstance> getDocumentInstances() {
		return docInstancesVec;
	}

	public Vector<Double> getFeaturesIDFValues() {
		return featuresIDFValuesVec;
	}
}
