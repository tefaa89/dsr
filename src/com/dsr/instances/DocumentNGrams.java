package com.dsr.instances;

import java.util.Vector;
import com.dsr.util.enumu.NGramEnum;

public class DocumentNGrams extends DocumentInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NGramEnum nGramType;
	private Vector<String> nGramVec; // Document content split into tokens

	public DocumentNGrams(DocumentInfo docInfo, Vector<String> nGramVec, NGramEnum nGramType) {
		super(docInfo.getName(), docInfo.getCategory(), docInfo.getContent());
		this.nGramVec = nGramVec;
		this.nGramType = nGramType;
	}

	public DocumentNGrams(Vector<String> nGramVec, String name, String category, NGramEnum nGramType) {
		super(name, category, null);
		this.nGramVec = nGramVec;
		this.nGramType = nGramType;
	}

	public void setnGramType(NGramEnum nGramType) {
		this.nGramType = nGramType;
	}

	public NGramEnum getnGramType() {
		return nGramType;
	}

	public Vector<String> getnGramVec() {
		return nGramVec;
	}

	@Override
	public String toString() {
		return "===> Name: " + getName() + "\n     Category: " + getCategory() + "\n     Content: "
				+ getContent() + "\n     NGramType: " + nGramType + "\n     NGram: "
				+ nGramVec.toString()
				+ "\n ========================================================\n";
	}
}
