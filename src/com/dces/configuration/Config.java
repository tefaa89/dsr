package com.dces.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import com.dces.util.xml.DCESInfoXML;

public class Config {
	private static Logger logger = (Logger) LoggerFactory.getLogger(Config.class);
	private static final String FS_EVAL_FILENAME = "fs_evaluators_config.xml";
	private static final String FS_SEARCH_FILENAME = "fs_search_methods_config.xml";
	private static final String CLASSIFIERS_FILENAME = "classifiers_config.xml";

	private static Element fsEvaluatorsXmlRoot;
	private static Element fsSearchMethodsXmlRoot;
	private static Element classifiersXMLRoot;

	public static void initConfiguration() {
		logger.debug("Calling: initConfiguration():void method");
		Builder builder = new Builder();
		try {
			// Reading Feature Selection Evaluators Config File
			logger.trace("Reading {} file", FS_EVAL_FILENAME);
			File file = new File("resources//configuration//" + FS_EVAL_FILENAME);
			Document doc = builder.build(file);
			fsEvaluatorsXmlRoot = doc.getRootElement();
			logger.trace("{} file is successfuly read", FS_EVAL_FILENAME);

			// Reading Feature Selection Search Methods Config File
			logger.trace("Reading {} file", FS_SEARCH_FILENAME);
			file = new File("resources//configuration//" + FS_SEARCH_FILENAME);
			doc = builder.build(file);
			fsSearchMethodsXmlRoot = doc.getRootElement();
			logger.trace("{} file is successfuly read", FS_SEARCH_FILENAME);

			// Reading Classifiers Config File
			logger.trace("Reading {} file", CLASSIFIERS_FILENAME);
			file = new File("resources//configuration//" + CLASSIFIERS_FILENAME);
			doc = builder.build(file);
			logger.trace("{} file is successfuly read", CLASSIFIERS_FILENAME);
			classifiersXMLRoot = doc.getRootElement();
		} catch (Exception e) {
			logger.error("Building XML Files: {}", e);
		}
	}

	public static DCESInfoXML getClassifierByID(String id) {
		logger.debug("Calling: getClassifierByID(String):ClassifierInfoXML method");
		return getDCESInfoByID(id, classifiersXMLRoot);
	}

	public static ArrayList<DCESInfoXML> getFSEvaluatorsInfo() {
		logger.debug("Calling: getFSEvaluatorsInfo():ArrayList<DCESInfoXML> method");
		ArrayList<DCESInfoXML> fsEvalInfoList = new ArrayList<DCESInfoXML>();
		Elements elements = fsEvaluatorsXmlRoot.getChildElements();
		// Loop on classifiers elements
		for (int i = 0; i < elements.size(); i++) {
			String id = elements.get(i).getAttributeValue("id");
			DCESInfoXML infoXML = getDCESInfoByID(id, elements.get(i));
			if (infoXML != null)
				fsEvalInfoList.add(infoXML);
		}
		return fsEvalInfoList;
	}

	public static ArrayList<DCESInfoXML> getFSSeachMethodInfo() {
		logger.debug("Calling: getFSSeachMethodInfo():ArrayList<DCESInfoXML> method");
		ArrayList<DCESInfoXML> fsSearchInfoList = new ArrayList<DCESInfoXML>();
		Elements elements = fsSearchMethodsXmlRoot.getChildElements();
		// Loop on classifiers elements
		for (int i = 0; i < elements.size(); i++) {
			String id = elements.get(i).getAttributeValue("id");
			DCESInfoXML infoXML = getDCESInfoByID(id, elements.get(i));
			if (infoXML != null)
				fsSearchInfoList.add(infoXML);
		}
		return fsSearchInfoList;
	}

	/**
	 * Fetches all the classifiers info form the XML
	 * 
	 * @return {@link ArrayList}
	 */
	public static ArrayList<DCESInfoXML> getClassifiersInfo() {
		logger.debug("Calling: getClassifiersInfo():ArrayList<DCESInfoXML> method");
		ArrayList<DCESInfoXML> classifiersInfoList = new ArrayList<DCESInfoXML>();
		Elements elements = classifiersXMLRoot.getChildElements();
		// Loop on classifiers elements
		for (int i = 0; i < elements.size(); i++) {
			String id = elements.get(i).getAttributeValue("id");
			DCESInfoXML infoXML = getDCESInfoByID(id, elements.get(i));
			if (infoXML != null)
				classifiersInfoList.add(infoXML);
		}
		return classifiersInfoList;
	}

	/**
	 * Every element in an XML file is an DCESInfoXml object
	 * 
	 * @param id
	 * @param currentElement
	 * @return {@link DCESInfoXML}
	 */
	private static DCESInfoXML getDCESInfoByID(String id, Element currentElement) {
		logger.debug("Calling: getDCESInfoByID(String, Element):DCESInfoXML method");
		DCESInfoXML dcesInfoXml = new DCESInfoXML();
		currentElement = validateElementandID(id, currentElement);
		// If after searching the xml file the element with id "id" is not found
		// return null
		String useAttributeValue = currentElement.getAttributeValue("use");
		if (currentElement.getAttribute("id") == null
				|| (useAttributeValue != null && useAttributeValue.equals("false")))
			return null;
		// At this point we are sure that there is an element with id "id"
		String className = currentElement.getAttributeValue("classPath");
		Map<String, String[]> paramters = getParametersFromElement(currentElement);
		ArrayList<String> searchMethodsIDList = getSearchMethodsIDList(currentElement);

		dcesInfoXml.setID(id);
		dcesInfoXml.setClassName(className);
		dcesInfoXml.setParameters(paramters);
		dcesInfoXml.setEvaluatorSearchMethodsIDList(searchMethodsIDList);
		return dcesInfoXml;
	}

	private static Element validateElementandID(String id, Element currentElement) {
		// If the current element is null or not equal to the id string, then
		// starting from the root search for the element with id "id"
		if (currentElement.getAttribute("id") == null
				|| !currentElement.getAttribute("id").equals(id)) {
			Elements elements = classifiersXMLRoot.getChildElements();
			// Loop on classifiers elements
			for (int i = 0; i < elements.size(); i++) {
				String idSearch = elements.get(i).getAttributeValue("id");
				if (idSearch.equals(id)) {
					currentElement = elements.get(i);
					break;
				}
			}
		}
		return currentElement;
	}

	private static Map<String, String[]> getParametersFromElement(Element element) {
		Map<String, String[]> paramters = new HashMap<String, String[]>();
		element = element.getFirstChildElement("parameters");
		Elements paramElements = element.getChildElements();
		// Loop on parameters elements
		for (int j = 0; j < paramElements.size(); j++) {
			Elements valuesElements = paramElements.get(j).getChildElements();
			String paramOption = paramElements.get(j).getAttributeValue("option");
			ArrayList<String> values = new ArrayList<String>();
			// Loop on values elements
			for (int k = 0; k < valuesElements.size(); k++) {
				String useAttributeValue = valuesElements.get(k).getAttributeValue("use");
				if (useAttributeValue != null && useAttributeValue.equals("false"))
					continue;
				values.add(valuesElements.get(k).getValue());
			}
			paramters.put(paramOption, values.toArray(new String[0]));
		}
		return paramters;
	}

	private static ArrayList<String> getSearchMethodsIDList(Element element) {
		ArrayList<String> searchMethodsIDList = new ArrayList<String>();
		element = element.getFirstChildElement("searchMethods");
		if (element != null) {
			Elements searchElements = element.getChildElements();
			for (int i = 0; i < searchElements.size(); i++) {
				Element searchElement = searchElements.get(i);
				String searchRefID = searchElement.getAttributeValue("refID");
				searchMethodsIDList.add(searchRefID);
			}
		}
		return searchMethodsIDList;
	}

	public static String getLangDetectorProfiles() {
		Element element = fsEvaluatorsXmlRoot.getFirstChildElement("LanguageDetectorProfiles");
		if (element != null)
			return element.getValue();
		return "";
	}
}
