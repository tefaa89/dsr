package com.dsr.instances;

import java.util.Vector;
import com.dsr.util.enumu.NGramEnum;

public class DocumentInstancesBuilder {

	private Vector<DocumentInfo> docInfoVec;

	private Vector<DocumentNGrams> docNGramVec;

	private DocumentInstances docInstances;

	private DocumentInstancesInfo docInstancesInfo;

	private boolean buildFeatures;

	private boolean effictiveInstances;

	@SuppressWarnings("unchecked")
	public DocumentInstancesBuilder(Vector<?> docInstorDocInfoVec,
			DocumentInstancesInfo docInstancesInfo, boolean isDocInfo) {
		buildFeatures = false;
		effictiveInstances = false;
		if (isDocInfo) {
			this.docInfoVec = (Vector<DocumentInfo>) docInstorDocInfoVec;
			this.docInstancesInfo = docInstancesInfo;
		} else {
			Vector<DocumentInstance> docInstanceVec = (Vector<DocumentInstance>) docInstorDocInfoVec;
			docNGramVec = new Vector<DocumentNGrams>();
			this.docInstancesInfo = docInstancesInfo;

			for (DocumentInstance docInstance : docInstanceVec) {
				DocumentNGrams docNGram = new DocumentNGrams(docInstance.getDocNGram()
						.getnGramVec(), docInstance.getDocNGram().getDocID(), docInstance
						.getDocNGram().getName(), docInstance.getDocNGram().getCategory(),
						docInstance.getDocNGram().getnGramType());
				docNGramVec.add(docNGram);
			}
		}
	} 

	public void buildInstances() {
		buildNGrams();
		buildFeatures();
		buildCategories();
		buildFeaturesValues();
		setInstancesEffectiveness();
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
		if (buildFeatures) {
			DocumentFeaturesBuilder dfb = new DocumentFeaturesBuilder(docNGramVec);
			dfb.buildFeatures();
			docInstancesInfo.setFeaturesVec(dfb.getFeaturesVec());
		}
	}

	private void buildCategories() {
		if (!buildFeatures)
			return;
		Vector<String> categoriesVec = new Vector<String>();
		for (DocumentNGrams doc : docNGramVec) {
			if (!categoriesVec.contains(doc.getCategory()))
				categoriesVec.add(doc.getCategory());
		}
		docInstancesInfo.setCategoriesVec(categoriesVec);
	}

	private void buildFeaturesValues() {
		if (docInstancesInfo.getFeaturesVec() == null)
			return;
		DocumentValuesBuilder dvb = new DocumentValuesBuilder(docInstancesInfo.getFeaturesVec(),
				docInstancesInfo.getFeaturesType(), docNGramVec);
		dvb.build();
		docInstances = new DocumentInstances(dvb.getDocumentInstances());
		docInstances.setDocInstancesInfo(docInstancesInfo);
		docInstances.setFeaturesIDFVec(dvb.getFeaturesIDFValues());
	}

	private void setInstancesEffectiveness()
	{
		for(DocumentInstance docInstance: docInstances)
			docInstance.setEffective(effictiveInstances);
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

	public boolean isBuildFeatures() {
		return buildFeatures;
	}

	public void setBuildFeatures(boolean buildFeatures) {
		this.buildFeatures = buildFeatures;
	}

	public void setEffictiveInstances(boolean effictiveInstances)
	{
		this.effictiveInstances = effictiveInstances;
	}
}
