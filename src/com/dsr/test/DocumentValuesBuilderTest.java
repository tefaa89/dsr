package com.dsr.test;

import java.util.Vector;
import com.dsr.instances.DocumentInfo;
import com.dsr.instances.DocumentNGrams;
import com.dsr.instances.DocumentValuesBuilder;
import com.dsr.util.enumu.FeatureValuesEnum;
import com.dsr.util.enumu.NGramEnum;

public class DocumentValuesBuilderTest {
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
		DocumentValuesBuilder dvb = new DocumentValuesBuilder(features, FeatureValuesEnum.TFIDF_VALUES, docNGramVec);
		dvb.build();
		System.out.println(dvb.getDocumentInstances());
	}
}
