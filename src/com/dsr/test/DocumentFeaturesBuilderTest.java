package com.dsr.test;

import java.util.Vector;
import com.dsr.instances.DocumentFeaturesBuilder;
import com.dsr.instances.DocumentInfo;
import com.dsr.instances.DocumentNGrams;
import com.dsr.util.enumu.NGramEnum;

public class DocumentFeaturesBuilderTest {
	public static void main(String[] args) {
		Vector<DocumentNGrams> docNGramVec = new Vector<DocumentNGrams>();
		Vector<String> ngram = new Vector<String>();
		ngram.add("he");
		ngram.add("le");
		ngram.add("eo");
		ngram.add("he");
		DocumentNGrams d = new DocumentNGrams(new DocumentInfo("D1", "Cat", "Hello People"), ngram,
				NGramEnum.WORD_UNIGRAM);
		docNGramVec.add(d);
		Vector<String> ngram2 = new Vector<String>();
		ngram.add("2me");
		ngram.add("2ne");
		ngram.add("2me");
		ngram.add("2ne");
		DocumentNGrams d2 = new DocumentNGrams(new DocumentInfo("D2", "Cat2", "Hello People 2"),
				ngram2, NGramEnum.WORD_UNIGRAM);
		docNGramVec.add(d2);
		DocumentFeaturesBuilder dF = new DocumentFeaturesBuilder(docNGramVec);
		dF.buildFeatures();
		System.out.println(dF.getFeaturesVec());
	}
}
