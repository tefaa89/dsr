package com.dsr.classifier;

import java.io.Serializable;
import com.dsr.instances.DocumentInstance;
import com.dsr.instances.DocumentInstances;
import com.dsr.util.enumu.ClassifiersEnum;
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
}
