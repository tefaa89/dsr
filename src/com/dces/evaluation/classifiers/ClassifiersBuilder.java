package com.dces.evaluation.classifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import com.dces.evaluation.BuilderAbstract;
import com.dces.util.xml.DCESInfoXML;

public class ClassifiersBuilder extends BuilderAbstract {
	private static Logger logger = (Logger) LoggerFactory.getLogger(ClassifiersBuilder.class);
	private Map<String, ArrayList<DocumentClassifer>> classifiersMap;

	public ClassifiersBuilder() {
		classifiersMap = new HashMap<String, ArrayList<DocumentClassifer>>();
	}

	public ArrayList<DocumentClassifer> getClassifiersWithDefaultSettings() {
		ArrayList<DocumentClassifer> res = new ArrayList<DocumentClassifer>();
		for (String key : classifiersMap.keySet())
			res.add(classifiersMap.get(key).get(0));
		return res;
	}

	public Map<String, ArrayList<DocumentClassifer>> getClassifiersExcludingDefaultSettings() {
		Map<String, ArrayList<DocumentClassifer>> res = new HashMap<String, ArrayList<DocumentClassifer>>();
		for (String key : classifiersMap.keySet()) {
			if (classifiersMap.get(key).size() <= 1)
				continue;
			res.put(key, classifiersMap.get(key));
			res.get(key).remove(0);
		}
		return res;
	}

	public Map<String, ArrayList<DocumentClassifer>> getClassifiersMap() {
		return classifiersMap;
	}

	public void build(ArrayList<DCESInfoXML> classifierInfoXMLList) {
		logger.debug("Calling: build(ArrayList<ClassifierInfoXML>):void method");
		logger.trace("Start Loop");
		logger.trace("Looping on each classifier found in the xml file to wrap it in DocumentClassifier object");
		for (DCESInfoXML classifierInfoXml : classifierInfoXMLList) {
			logger.trace("Wrapping:\n{}", classifierInfoXml);

			ArrayList<Map<String, String>> currentClassifierOptionsList = getOptions(classifierInfoXml);
			for (Map<String, String> currentClassifierOption : currentClassifierOptionsList) {
				String classifierClassPath = classifierInfoXml.getClassName();
				DocumentClassifer docClassifier = new DocumentClassifer();
				docClassifier.setClassPath(classifierClassPath);
				docClassifier.setOptions(currentClassifierOption);
				if (!classifiersMap.containsKey(classifierClassPath))
					classifiersMap.put(classifierClassPath, new ArrayList<DocumentClassifer>());
				classifiersMap.get(classifierClassPath).add(docClassifier);
			}
		}
		logger.trace("End Loop");
	}
}
