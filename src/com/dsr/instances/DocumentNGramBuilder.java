package com.dsr.instances;

import java.io.StringReader;
import java.util.Vector;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.shingle.ShingleFilter;
import org.apache.lucene.util.Version;
import com.dsr.util.LanguageDetector;
import com.dsr.util.StringAnalyzer;
import com.dsr.util.TokenStreamUtil;
import com.dsr.util.enumu.LanguageEnum;
import com.dsr.util.enumu.NGramEnum;

public class DocumentNGramBuilder {
	private Vector<DocumentInfo> docInfoVec;
	private Vector<DocumentNGrams> docNGramVec;
	private NGramEnum nGramType;

	public DocumentNGramBuilder(Vector<DocumentInfo> docInfoVec, NGramEnum nGramType) {
		this.docInfoVec = docInfoVec;
		this.nGramType = nGramType;
		docNGramVec = new Vector<DocumentNGrams>();
	}

	public void build() {
		switch (nGramType) {
		case UNIGRAM:
			buildCharNGram(1, 1);
			break;
		case BIGRAM:
			buildCharNGram(2, 2);
			break;
		case TRIGRAM:
			buildCharNGram(3, 3);
			break;
		case WORD_UNIGRAM:
			buildWordNGram(1, 1);
			break;
		case WORD_BIGRAM:
			buildWordNGram(2, 2);
			break;
		case WORD_TRIGRAM:
			buildWordNGram(3, 3);
		default:
			break;
		}
	}

	private void buildCharNGram(int minGram, int maxGram) {

		docNGramVec.clear();
		for (DocumentInfo docInfo : docInfoVec) {
			TokenStream nGramTokenizer = new NGramTokenizer(new StringReader(docInfo.getContent()),
					minGram, maxGram);
			TokenStream filteredNGram = new LowerCaseFilter(Version.LUCENE_36, nGramTokenizer);
			docNGramVec.add(new DocumentNGrams(docInfo, TokenStreamUtil
					.tokenStreamToVector(filteredNGram), nGramType));
		}

	}

	public void buildWekaCharNGram(int minGram, int maxGram) {
		weka.core.tokenizers.NGramTokenizer ngT = new weka.core.tokenizers.NGramTokenizer();

		ngT.setNGramMinSize(minGram);
		ngT.setNGramMaxSize(maxGram);
		ngT.tokenize("Hello my name is tefa fa tefa fa");
		while (ngT.hasMoreElements())
			System.out.println(ngT.nextElement());
	}

	private void buildWordNGram(int minGram, int maxGram) {

		docNGramVec.clear();
		LanguageDetector langDet = new LanguageDetector();
		for (DocumentInfo dInfo : docInfoVec) {
			Vector<String> ngramVec;
			LanguageEnum lang = langDet.detectLang(dInfo.getContent());
			TokenStream stemmedStream = StringAnalyzer.removeStopWordsAndStem(dInfo.getContent(),
					lang);
			if (minGram < 2 || maxGram < 2)
				ngramVec = TokenStreamUtil.tokenStreamToVector(stemmedStream);
			else {
				ShingleFilter sf = new ShingleFilter(stemmedStream, minGram, maxGram);
				sf.setOutputUnigrams(false);
				ngramVec = TokenStreamUtil.tokenStreamToVector(sf);
			}
			docNGramVec.add(new DocumentNGrams(dInfo, ngramVec, nGramType));
		}
	}

	public Vector<DocumentNGrams> getDocNGramVec() {
		return docNGramVec;
	}
}
