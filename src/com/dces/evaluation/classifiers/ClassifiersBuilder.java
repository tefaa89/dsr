package com.dces.evaluation.classifiers;

import java.util.ArrayList;
import java.util.Map;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import com.dces.evaluation.BuilderAbstract;
import com.dces.util.xml.DCESInfoXML;

public class ClassifiersBuilder extends BuilderAbstract {
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

			ArrayList<Map<String, String>> currentClassifierOptionsList = getOptions(classifierInfoXml);
			for (Map<String, String> currentClassifierOption : currentClassifierOptionsList) {
				DocumentClassifer docClassifier = new DocumentClassifer();
				docClassifier.setClassPath(classifierInfoXml.getClassName());
				docClassifier.setOptions(currentClassifierOption);
				classifierList.add(docClassifier);
			}
		}
		logger.trace("End Loop");
	}
}
