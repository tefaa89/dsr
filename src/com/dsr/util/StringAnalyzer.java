package com.dsr.util;

import java.io.File;
import java.io.StringReader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.util.Version;
import com.dsr.util.enumu.LanguageEnum;
import weka.core.stemmers.SnowballStemmer;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class StringAnalyzer {

	public static TokenStream removeStopWordsAndStem(String docContent, LanguageEnum language) {
		Analyzer analyzer = null;
		switch (language) {
		case ENGLISH:
			analyzer = new EnglishAnalyzer(Version.LUCENE_36);
			break;
		case GERMAN:
			analyzer = new GermanAnalyzer(Version.LUCENE_36);
			break;
		default:
			break;
		}
		TokenStream stream = analyzer.tokenStream("content", new StringReader(docContent));
		analyzer.close();
		return stream;
	}
	
	public static TokenStream removeStopWordsAndStemWeka(String docContent, LanguageEnum language) {
		SnowballStemmer s = new SnowballStemmer();
		s.setStemmer("english");
		StringToWordVector st = new StringToWordVector();
		st.setStemmer(s);
		File stopwords = new File("algorithms\\english\\stop.txt");
		st.setStopwords(stopwords);
		st.setUseStoplist(true);
		return null;
	}
	
	public static void main(String[] args) {
		StringAnalyzer.removeStopWordsAndStem("", LanguageEnum.ENGLISH);
	}
}
