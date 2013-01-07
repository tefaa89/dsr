package com.dces.evaluation.classifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.dces.util.ClassifierInfoXML;

public class ClassifiersBuilder {
	private ArrayList<DocumentClassifer> classifierList;

	public ClassifiersBuilder() {
		classifierList = new ArrayList<DocumentClassifer>();
	}

	public ArrayList<DocumentClassifer> getClassifiersWithDefaultSettings() {
		ArrayList<String> classifiersSofar = new ArrayList<String>();
		ArrayList<DocumentClassifer> res = new ArrayList<DocumentClassifer>();
		for (DocumentClassifer classifier : classifierList) {
			String classPath = classifier.getClassPath();
			if (!classifiersSofar.contains(classPath)) {
				classifiersSofar.add(classPath);
				res.add(classifier);
			}
		}
		return res;
	}

	public ArrayList<DocumentClassifer> getClassifierList() {
		return classifierList;
	}

	public void build(ArrayList<ClassifierInfoXML> classifierInfoXMLList) {
		for (ClassifierInfoXML classifierInfoXml : classifierInfoXMLList) {
			Map<String, String[]> parametersMap = classifierInfoXml.getParameters();
			int numOfParamters = parametersMap.size();
			String[] keys = parametersMap.keySet().toArray(new String[0]);
			int[] indexArr = new int[numOfParamters];
			while (true) {
				Map<String, String> currentOptions = new HashMap<String, String>();
				for (int i = 0; i < indexArr.length; i++)
					currentOptions.put(keys[i], parametersMap.get(keys[i])[indexArr[i]]);
				DocumentClassifer docClassifier = new DocumentClassifer();
				docClassifier.setClassPath(classifierInfoXml.getClassName());
				classifierList.add(docClassifier);
				for (int i = indexArr.length - 1; i >= 0; i--) {
					indexArr[i]++;
					if (indexArr[i] < parametersMap.get(keys[i]).length)
						break;
					if (i != 0)
						indexArr[i] = 0;
				}
				if (indexArr[0] >= parametersMap.get(keys[0]).length)
					break;
			}
		}

	}
}
