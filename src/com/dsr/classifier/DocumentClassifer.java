package com.dsr.classifier;

import java.io.Serializable;
import java.util.Vector;
import weka.classifiers.Classifier;
import weka.core.Instances;
import com.dsr.database.DBQuery;
import com.dsr.instances.DocumentInstance;
import com.dsr.instances.DocumentInstances;
import com.dsr.instances.DocumentInstancesBuilder;
import com.dsr.util.DSRWekaUtil;
import com.dsr.util.enumu.ClassifiersEnum;
import com.dsr.util.enumu.FeatureValuesEnum;
import com.dsr.instances.DocumentInstancesInfo;

public abstract class DocumentClassifer implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private DocumentInstancesInfo docInstancesInfo;
	private ClassifiersEnum classifierType;

	public abstract int classifyDocumentInstance(DocumentInstance docInstance);

	public abstract int[] classifyDocumentInstances(DocumentInstances docInstances);

	public abstract void updateClassifier(DocumentInstances docInstances);

	public boolean isClassifierTrainined() {
		return docInstancesInfo == null ? false : true;
	}

	public ClassifiersEnum getClassifierType() {
		return classifierType;
	}

	public void setClassifiersEnum(ClassifiersEnum classifierType) {
		this.classifierType = classifierType;
	}

	public DocumentInstancesInfo getDocInstancesInfo() {
		return docInstancesInfo;
	}

	public void setDocInstancesInfo(DocumentInstancesInfo docInstancesInfo) {
		this.docInstancesInfo = docInstancesInfo;
	}

	protected void updateClassifier(DocumentInstances docInstances, Classifier classifier) {
		/*
		 * 1- Check for new category 2- If new category found continue, else go
		 * to step 3- Load old training data (All Document Instance entries in
		 * DB) 4- Add old training data to new training data 5- Build training
		 * instances 6- Train Classifier 7- Update DocumentInstancesInfo
		 */
		FeatureValuesEnum featuresType = getDocInstancesInfo().getFeaturesType();
		Instances wekaInstances;
		if (containsNewCategory(docInstances)) {
			Vector<DocumentInstance> trainingDataVec = getOldTrainingData();
			trainingDataVec.addAll(docInstances.getDocInstanceVec());
			DocumentInstancesBuilder docInstancesBuilder = new DocumentInstancesBuilder(
					trainingDataVec);
			docInstancesBuilder.buildInstances();
			wekaInstances = DSRWekaUtil.convertDocInstancesToWekaInstances(
					docInstancesBuilder.getDocumentInstances(), featuresType);
		} else {
			wekaInstances = DSRWekaUtil.convertDocInstancesToWekaInstances(docInstances,
					featuresType);
		}
		try {
			classifier.buildClassifier(wekaInstances);
		} catch (Exception e) {
			e.printStackTrace();
		}
		updateDatabase(docInstances.getDocInstanceVec());
	}

	protected boolean containsNewCategory(DocumentInstances docIntances) {
		Vector<String> newCategories = docIntances.getCategoriesVec();
		for (String category : newCategories) {
			if (docInstancesInfo.getCategoriesVec().contains(category))
				return true;
		}
		return false;
	}

	protected Vector<DocumentInstance> getOldTrainingData() {
		Vector<DocumentInstance> docInstances = new Vector<DocumentInstance>();
		return docInstances;
	}

	protected void updateDatabase(Vector<DocumentInstance> instanceVec) {
		DBQuery.storeTrainingDocumentInstaces(instanceVec);
	}
}
