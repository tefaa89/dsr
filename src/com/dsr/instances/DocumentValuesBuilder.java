package com.dsr.instances;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import com.dsr.util.enumu.NGramEnum;

public class DocumentValuesBuilder {

	private Vector<String> features;

	private Vector<DocumentNGrams> docNGramVec;

	private Vector<DocumentInstance> docInstancesVec;

	private Vector<Double> featuresIDFValuesVec;

	public DocumentValuesBuilder(Vector<String> features, Vector<DocumentNGrams> docNGramVec) {
		this.features = features;
		this.docNGramVec = docNGramVec;
	}

	public void build() {
		docInstancesVec = new Vector<DocumentInstance>();
		for (DocumentNGrams docNGram : docNGramVec) {
			DocumentInstance docInstance = new DocumentInstance(docNGram);
			Vector<Integer> docFValues = calculateFValues(docNGram.getnGramVec());
			Vector<Double> docTFValues = calculateTFValues(docFValues);
			docInstance.setFeaturesFValues(docFValues);
			docInstance.setFeaturesTFValues(docTFValues);
			docInstancesVec.add(docInstance);
		}
		buildIDFValuesVec();
		for (DocumentInstance docInstance : docInstancesVec) {
			Vector<Double> currentTFVec = docInstance.getFeaturesTFValues();
			docInstance.setFeaturesTFIDFValuesVec(multiplyTF_IDF(currentTFVec));
		}
	}

	private Vector<Integer> calculateFValues(Vector<String> nGramVec) {
		Map<String, Integer> wordFreqMap = new HashMap<String, Integer>();
		for (String word : nGramVec) {
			Integer count = wordFreqMap.get(word);
			wordFreqMap.put(word, (count == null) ? 1 : count + 1);
		}
		Vector<Integer> wordFreqVec = new Vector<Integer>();
		for (String word : features) {
			if (wordFreqMap.containsKey(word))
				wordFreqVec.add(wordFreqMap.get(word));
			else
				wordFreqVec.add(0);
		}
		return wordFreqVec;
	}

	private Vector<Double> calculateTFValues(Vector<Integer> fValues) {
		Vector<Double> tfValues = new Vector<Double>();
		// Iterate on fValues and calculate squrt of each value;
		Integer maxFreq = Collections.max(fValues);
		for (Integer fValue : fValues)
			tfValues.add(fValue / (maxFreq + Double.MIN_VALUE));
		return tfValues;
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

	private int getNumOfDocumentsContainingTerm(String term) {
		int res = 0;
		int termIndex = features.indexOf(term);
		for (DocumentInstance docInstance : docInstancesVec)
			res += docInstance.getFeaturesFValues().get(termIndex) > 0 ? 1 : 0;
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

	public static void main(String[] args) {
		Vector<DocumentNGrams> docNGramVec = new Vector<DocumentNGrams>();
		Vector<String> ngram = new Vector<String>();
		ngram.add("a");
		ngram.add("b");
		ngram.add("c");
		ngram.add("a");
		DocumentNGrams d = new DocumentNGrams(new DocumentInfo("D1", "Cat", "Hello People"), ngram,
				NGramEnum.WORD_UNIGRAM);
		docNGramVec.add(d);
		Vector<String> ngram2 = new Vector<String>();
		ngram2.add("c");
		ngram2.add("c");
		ngram2.add("b");
		ngram2.add("c");
		DocumentNGrams d2 = new DocumentNGrams(new DocumentInfo("D2", "Cat2", "Hello People 2"),
				ngram2, NGramEnum.WORD_UNIGRAM);
		docNGramVec.add(d2);
		Vector<String> features = new Vector<String>();
		features.add("d");
		features.add("a");
		features.add("b");
		features.add("c");
		DocumentValuesBuilder dvb = new DocumentValuesBuilder(features, docNGramVec);
		dvb.build();
	}
}
