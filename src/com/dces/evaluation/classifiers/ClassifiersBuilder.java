package com.dces.evaluation.classifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import com.dces.util.xml.DCESInfoXML;

public class ClassifiersBuilder {
	private static Logger logger = (Logger) LoggerFactory.getLogger(ClassifiersBuilder.class);
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

	public ArrayList<DocumentClassifer> getClassifiersExcludingDefaultSettings() {
		ArrayList<DocumentClassifer> classifiersWithDefaultSettings = getClassifiersWithDefaultSettings();
		ArrayList<DocumentClassifer> res = new ArrayList<DocumentClassifer>();
		for (DocumentClassifer classifier : classifierList) {
			if (!classifiersWithDefaultSettings.contains(classifier))
				res.add(classifier);
		}
		return res;
	}

	public ArrayList<DocumentClassifer> getClassifierList() {
		return classifierList;
	}
	
	public void build(ArrayList<DCESInfoXML> classifierInfoXMLList) {
		logger.debug("Calling: build(ArrayList<ClassifierInfoXML>):void method");
		logger.trace("Start Loop");
		logger.trace("Looping on each classifier found in the xml file to wrap it in DocumentClassifier object");
		for (DCESInfoXML classifierInfoXml : classifierInfoXMLList) {
			logger.trace("Wrapping:\n{}", classifierInfoXml);
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
				docClassifier.setOptions(currentOptions);
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
		logger.trace("End Loop");
	}
}
