package com.dsr.classifier;

import java.io.Serializable;
import java.util.Vector;
import weka.classifiers.Classifier;
import weka.classifiers.UpdateableClassifier;
import weka.core.Instance;
import weka.core.Instances;
import com.dsr.database.DBConnection;
import com.dsr.database.DBQuery;
import com.dsr.instances.DocumentInstance;
import com.dsr.instances.DocumentInstances;
import com.dsr.instances.DocumentInstancesBuilder;
import com.dsr.instances.DocumentInstancesInfo;
import com.dsr.util.DSRWekaUtil;
import com.dsr.util.enumu.ClassifiersEnum;

public abstract class DocumentClassifer implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private int classifierID;
	private DocumentInstancesInfo docInstancesInfo;
	private ClassifiersEnum classifierType;

	private boolean trainedClassifierBool;

	/**
	 *
	 * @param docInstances
	 * @return Vector<String>
	 */
	public abstract Vector<String> classifyDocumentInstances(DocumentInstances docInstances);

	/**
	 *
	 * @param docInstances
	 * @return Void
	 */
	public abstract void updateClassifier(DocumentInstances docInstances);

	/**
	 *
	 * @return The classifier type (ex, IBK, NB)
	 */
	public ClassifiersEnum getClassifierType() {
		return classifierType;
	}

	/**
	 *
	 * @param classifierType
	 * @return Void
	 */
	public void setClassifiersEnum(ClassifiersEnum classifierType) {
		this.classifierType = classifierType;
	}

	/**
	 *
	 * @return Document Instances Information Object
	 */
	public DocumentInstancesInfo getDocInstancesInfo() {
		return docInstancesInfo;
	}

	/**
	 *
	 * @param docInstancesInfo
	 */
	public void setDocInstancesInfo(DocumentInstancesInfo docInstancesInfo) {
		this.docInstancesInfo = docInstancesInfo;
	}

	/**
	 * 1- Check for new category 2- If new category found continue, else go to
	 * step 3- Load old training data (All Document Instance entries in DB) 4-
	 * Add old training data to new training data 5- Build training instances 6-
	 * Train Classifier 7- Update DocumentInstancesInfo
	 *
	 * @param docInstances
	 *            The new classified instances
	 * @param classifier
	 *            The classifier that is going to be trained
	 * @return Nothing
	 */
	protected void updateClassifier(DocumentInstances docInstances, Classifier classifier) {
		Instances wekaInstances;
		if (containsNewCategory(docInstances)) {
			Vector<DocumentInstance> trainingDataVec = getEffictiveDocumentInstaces();
			boolean oldTrainingDataExists = trainingDataVec.size() > 0;
			trainingDataVec.addAll(docInstances.getDocInstanceVec());
			DocumentInstances newDocInstances = docInstances;
			if (oldTrainingDataExists) {
				DocumentInstancesBuilder docInstancesBuilder = new DocumentInstancesBuilder(
						trainingDataVec, getDocInstancesInfo(), false);
				docInstancesBuilder.setBuildFeatures(true);
				docInstancesBuilder.buildInstances();
				newDocInstances = docInstancesBuilder.getDocumentInstances();
			}
			setDocInstancesInfo(newDocInstances.getDocInstancesInfo());
			try {
				// If there is only one category then don't build the
				// classifier just update the DB
				if (getDocInstancesInfo().getCategoriesVec().size() > 1) {
					wekaInstances = DSRWekaUtil.convertDocInstancesToWekaInstances(newDocInstances);
					classifier.buildClassifier(wekaInstances);
				}
				// TODO Should I set the trainedClassifierBool to true or not
				setTrainedClassifierBool(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			wekaInstances = DSRWekaUtil.convertDocInstancesToWekaInstances(docInstances);
			try {
				UpdateableClassifier updateClassifier = (UpdateableClassifier) classifier;
				for (Instance inst : wekaInstances)
					updateClassifier.updateClassifier(inst);
				setTrainedClassifierBool(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		updateDatabase(docInstances.getDocInstanceVec());
	}

	/**
	 *
	 * @param docInstances
	 * @param classifier
	 * @return Vector<String> that contains the classification strings for each
	 *         instance
	 */
	protected Vector<String> classifyDocumentInstances(DocumentInstances docInstances,
			Classifier classifier) {
		if (!isTrainedClassifierBool())
			return null;
		Instances instances = DSRWekaUtil.convertDocInstancesToWekaInstances(docInstances);
		Vector<String> res = new Vector<String>();
		for (Instance inst : instances) {
			try {
				if (docInstances.getCategoriesVec().size() == 1)
					res.add(docInstances.getCategoriesVec().firstElement());
				else {
					double classificationIndex = classifier.classifyInstance(inst);
					res.add(instances.classAttribute().value((int) classificationIndex));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//TODO Update Category in DB
		return res;
	}

	/**
	 *
	 * @param docIntances
	 * @return
	 */
	protected boolean containsNewCategory(DocumentInstances docIntances) {
		if (docInstancesInfo.getCategoriesVec() == null)
			return true;
		Vector<String> newCategories = docIntances.getCategoriesVec();
		for (String category : newCategories) {
			if (docInstancesInfo.getCategoriesVec().contains(category))
				return true;
		}
		return false;
	}

	/**
	 *
	 * @return
	 */
	protected Vector<DocumentInstance> getEffictiveDocumentInstaces() {
		Vector<DocumentInstance> docInstanceVec = DBQuery.getEffictiveDocumentInstaces(DBConnection
				.connect());
		if (docInstanceVec == null)
			return new Vector<DocumentInstance>();
		return docInstanceVec;
	}

	/**
	 *
	 * @param instanceVec
	 */
	protected void updateDatabase(Vector<DocumentInstance> instanceVec) {
		DBQuery.storeTrainingDocumentInstaces(DBConnection.connect(), instanceVec);
	}

	/**
	 *
	 * @return
	 */
	public boolean isTrainedClassifierBool() {
		return trainedClassifierBool;
	}

	/**
	 *
	 * @param trainedClassifierBool
	 */
	public void setTrainedClassifierBool(boolean trainedClassifierBool) {
		this.trainedClassifierBool = trainedClassifierBool;
	}

	public int getClassifierID() {
		return classifierID;
	}

	public void setClassifierID(int classifierID) {
		this.classifierID = classifierID;
	}
}
