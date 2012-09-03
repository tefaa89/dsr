package com.dsr.test;

import com.dsr.database.DBConnection;
import com.dsr.database.DBQuery;
import com.dsr.instances.DocumentInfo;
import com.dsr.util.StringAnalyzer;
import com.dsr.util.TokenStreamUtil;
import com.dsr.util.Trace;
import com.dsr.util.enumu.LanguageEnum;

public class StringAnalyzerTest {
	public static void main(String[] args) {
		String engText = "I really have to work the whole weekend. Actually, it's not a weekend anymore 'Working the whole week.";
		String germanText = "er das die wo fuhr fuhrst fuhren fuhrt";
		DocumentInfo[] dinfo = DBQuery.getAllDocuments(DBConnection.connect());
		String content = dinfo[0].getContent();
		Trace.trace(StringAnalyzer.removeStopWordsAndStem(content, LanguageEnum.GERMAN));
		Trace.trace(TokenStreamUtil.tokenStreamToVector(StringAnalyzer.removeStopWordsAndStem(engText, LanguageEnum.ENGLISH)));
		Trace.trace(TokenStreamUtil.tokenStreamToVector(StringAnalyzer.removeStopWordsAndStem(germanText, LanguageEnum.GERMAN)));
	}
}
