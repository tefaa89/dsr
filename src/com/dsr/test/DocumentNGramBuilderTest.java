package com.dsr.test;

import java.util.Vector;
import com.dsr.instances.DocumentInfo;
import com.dsr.instances.DocumentNGramBuilder;
import com.dsr.util.enumu.NGramEnum;

public class DocumentNGramBuilderTest {
	public static void main(String[] args) {
		DocumentInfo dinfo = new DocumentInfo("Test", "Cat", "Hello How are you you?");
		Vector<DocumentInfo> dinfoVec = new Vector<DocumentInfo>();
		dinfoVec.add(dinfo);
		DocumentNGramBuilder dngrambuilder = new DocumentNGramBuilder(dinfoVec,
				NGramEnum.WORD_UNIGRAM);
		dngrambuilder.build();
		System.out.println(dngrambuilder.getDocNGramVec());
	}
}
