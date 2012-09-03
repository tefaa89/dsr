package com.dsr.util;

import java.io.IOException;
import java.util.Vector;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class TokenStreamUtil 
{
	public static Vector<String> tokenStreamToVector(TokenStream ts) {
		Vector<String> tokensVec = new Vector<String>();
		try {
			while (ts.incrementToken()) {
				tokensVec.add(ts.getAttribute(CharTermAttribute.class).toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tokensVec;
	}
}
