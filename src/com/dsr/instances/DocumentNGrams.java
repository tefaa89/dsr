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

	public DocumentNGrams()
	{
		super("","","");
		nGramType = NGramEnum.WORD_UNIGRAM;
		nGramVec = new Vector<String>();
	}

	public DocumentNGrams(DocumentInfo docInfo, Vector<String> nGramVec, NGramEnum nGramType) {
		super(docInfo.getName(), docInfo.getCategory(), docInfo.getContent());
		setDocID(docInfo.getDocID());
		this.nGramVec = nGramVec;
		this.nGramType = nGramType;
	}

	public DocumentNGrams(Vector<String> nGramVec, int docID, String name, String category, NGramEnum nGramType) {
		super(name, category, null);
		this.setDocID(docID);
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

	public void setnGramVec(Vector<String> nGramVec) {
		this.nGramVec = nGramVec;
	}
	@Override
	public String toString() {
		return "===> Name: " + getName() + "\n     Category: " + getCategory() + "\n     Content Size: "
				+ getContent().length() + "\n     NGramType: " + nGramType + "\n     NGram: "
				+ nGramVec.toString()
				+ "\n ========================================================\n";
	}
}
