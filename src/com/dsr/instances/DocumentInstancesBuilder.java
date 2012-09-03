package com.dsr.instances;

import java.util.Vector;
import com.dsr.util.enumu.NGramEnum;

public class DocumentInstancesBuilder {

	private Vector<DocumentInfo> docInfoVec;

	private Vector<DocumentNGrams> docNGramVec;

	private DocumentInstances docInstances;

	private DocumentInstancesInfo docInstancesInfo;

	public DocumentInstancesBuilder(Vector<DocumentInstance> docInstanceVec) {
		docNGramVec = new Vector<DocumentNGrams>();
		docInstancesInfo = new DocumentInstancesInfo();
		docInstancesInfo.setNGramType(docInstanceVec.firstElement().getnGramType());
		for (DocumentInstance docInstance : docInstanceVec) {
			docNGramVec.add(new DocumentNGrams(docInstance.getnGramVec(), docInstance.getName(),
					docInstance.getCategory(), docInstance.getnGramType()));
		}
	}

	public DocumentInstancesBuilder(Vector<DocumentInfo> docInfoVec, NGramEnum ngramType) {
		this.docInfoVec = docInfoVec;
		docInstancesInfo = new DocumentInstancesInfo();
		docInstancesInfo.setNGramType(ngramType);
	}

	public void buildInstances() {
		buildNGrams();
		buildFeatures();
		buildCategories();
		buildFeaturesValues();
	}

	private void buildNGrams() {
		if (docNGramVec == null) {
			if (docInstancesInfo.getNGramType() == null)
				docInstancesInfo.setNGramType(NGramEnum.WORD_UNIGRAM);
			DocumentNGramBuilder ngramBuilder = new DocumentNGramBuilder(docInfoVec,
					docInstancesInfo.getNGramType());
			ngramBuilder.build();
			docNGramVec = ngramBuilder.getDocNGramVec();
		}
	}

	private void buildFeatures() {
		DocumentFeaturesBuilder dfb = new DocumentFeaturesBuilder(docNGramVec);
		dfb.buildFeatures();
		docInstancesInfo.setFeaturesVec(dfb.getFeaturesVec());
	}

	private void buildCategories() {
		Vector<String> categoriesVec = new Vector<String>();
		for (DocumentNGrams doc : docNGramVec) {
			if (!categoriesVec.contains(doc.getCategory()))
				categoriesVec.add(doc.getCategory());
		}
		docInstancesInfo.setCategoriesVec(categoriesVec);
	}

	private void buildFeaturesValues() {
		DocumentValuesBuilder dvb = new DocumentValuesBuilder(docInstancesInfo.getFeaturesVec(),
				docNGramVec);
		dvb.build();
		docInstances = new DocumentInstances(dvb.getDocumentInstances());
		docInstances.setCategoriesVec(docInstancesInfo.getCategoriesVec());
		docInstances.setFeatures(docInstancesInfo.getFeaturesVec());
		docInstances.setFeaturesIDFVec(dvb.getFeaturesIDFValues());
	}

	public DocumentInstances getDocumentInstances() {
		return docInstances;
	}

	public Vector<DocumentNGrams> getDocNGramVec() {
		return docNGramVec;
	}

	public void setDocNGramVec(Vector<DocumentNGrams> docNGramVec) {
		this.docNGramVec = docNGramVec;
	}

	public Vector<DocumentInfo> getDocInfoVec() {
		return docInfoVec;
	}

	public void setDocVec(Vector<DocumentInfo> docInfoVec) {
		this.docInfoVec = docInfoVec;
	}

	public DocumentInstancesInfo getDocInstancesInfo() {
		return docInstancesInfo;
	}
}
