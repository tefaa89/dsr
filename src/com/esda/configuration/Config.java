package com.esda.configuration;

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
import com.esda.util.xml.ESInfoXML;
import com.esda.util.xml.ESInfoXmlParam;

public class Config {
	private static Logger logger = (Logger) LoggerFactory.getLogger(Config.class);
	private static final String FS_EVAL_FILENAME = "fsConfig.xml";
	private static final String FE_FILENAME = "feConfig.xml";
	private static final String CLASSIFIERS_FILENAME = "classifiersConfig.xml";

	private static Element fsEvaluatorsXmlRoot;
	private static Element fsSearchMethodsXmlRoot;
	private static Element feXmlRoot;
	private static Element classifiersXMLRoot;

	public static void initConfiguration() {
		logger.debug("Calling: initConfiguration():void method");
		Builder builder = new Builder();
		try {
			// Reading Feature Selection Evaluators Config File
			logger.trace("Reading {} file", FS_EVAL_FILENAME);
			File file = new File("resources//configuration//" + FS_EVAL_FILENAME);
			Document doc = builder.build(file);
			fsEvaluatorsXmlRoot = doc.getRootElement().getFirstChildElement("evaluators");
			fsSearchMethodsXmlRoot = doc.getRootElement().getFirstChildElement("searchMethods");
			logger.trace("{} file is successfuly read", FS_EVAL_FILENAME);

			// Reading Feature Extraction Methods Config File
			logger.trace("Reading {} file", FE_FILENAME);
			file = new File("resources//configuration//" + FE_FILENAME);
			doc = builder.build(file);
			feXmlRoot = doc.getRootElement();
			logger.trace("{} file is successfuly read", FE_FILENAME);

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

	public static ESInfoXML getClassifierByID(String id) {
		logger.debug("Calling: getClassifierByID(String):ClassifierInfoXML method");
		return getDCESInfoByID(id, classifiersXMLRoot);
	}

	public static ArrayList<ESInfoXML> getFSEvaluatorsInfo() {
		logger.debug("Calling: getFSEvaluatorsInfo():ArrayList<DCESInfoXML> method");
		ArrayList<ESInfoXML> fsEvalInfoList = new ArrayList<ESInfoXML>();
		Elements elements = fsEvaluatorsXmlRoot.getChildElements();
		// Loop on feature selection methods elements
		for (int i = 0; i < elements.size(); i++) {
			String id = elements.get(i).getAttributeValue("id");
			ESInfoXML infoXML = getDCESInfoByID(id, elements.get(i));
			if (infoXML != null)
				fsEvalInfoList.add(infoXML);
		}
		return fsEvalInfoList;
	}

	public static ArrayList<ESInfoXML> getFSSeachMethodInfo() {
		logger.debug("Calling: getFSSeachMethodInfo():ArrayList<DCESInfoXML> method");
		ArrayList<ESInfoXML> fsSearchInfoList = new ArrayList<ESInfoXML>();
		Elements elements = fsSearchMethodsXmlRoot.getChildElements();
		// Loop on classifiers elements
		for (int i = 0; i < elements.size(); i++) {
			String id = elements.get(i).getAttributeValue("id");
			ESInfoXML infoXML = getDCESInfoByID(id, elements.get(i));
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
	public static ArrayList<ESInfoXML> getClassifiersInfo() {
		logger.debug("Calling: getClassifiersInfo():ArrayList<DCESInfoXML> method");
		ArrayList<ESInfoXML> classifiersInfoList = new ArrayList<ESInfoXML>();
		Elements elements = classifiersXMLRoot.getChildElements();
		// Loop on classifiers elements
		for (int i = 0; i < elements.size(); i++) {
			String id = elements.get(i).getAttributeValue("id");
			ESInfoXML infoXML = getDCESInfoByID(id, elements.get(i));
			if (infoXML != null)
				classifiersInfoList.add(infoXML);
		}
		return classifiersInfoList;
	}

	/**
	 * Fetches all feature extraction methods from the XML
	 * @return {@link ArrayList}
	 */
	public static ArrayList<ESInfoXML> getFEMethodsInfo() {
		logger.debug("Calling: getFEMethodsInfo():ArrayList<DCESInfoXML> method");
		ArrayList<ESInfoXML> feInfoList = new ArrayList<ESInfoXML>();
		Elements elements = feXmlRoot.getChildElements();
		// Loop on feature extraction methods elements
		for (int i = 0; i < elements.size(); i++) {
			String id = elements.get(i).getAttributeValue("id");
			ESInfoXML infoXML = getDCESInfoByID(id, elements.get(i));
			if (infoXML != null)
				feInfoList.add(infoXML);
		}
		return feInfoList;
	}

	/**
	 * Every element in an XML file is an DCESInfoXml object
	 *
	 * @param id
	 * @param currentElement
	 * @return {@link ESInfoXML}
	 */
	private static ESInfoXML getDCESInfoByID(String id, Element currentElement) {
		logger.debug("Calling: getDCESInfoByID(String, Element):DCESInfoXML method");
		ESInfoXML dcesInfoXml = new ESInfoXML();
		currentElement = validateElementandID(id, currentElement);
		// If after searching the xml file the element with id "id" is not found
		// return null
		String useAttributeValue = currentElement.getAttributeValue("use");
		if (currentElement.getAttribute("id") == null
				|| (useAttributeValue != null && useAttributeValue.equals("false")))
			return null;
		// At this point we are sure that there is an element with id "id"
		String className = currentElement.getAttributeValue("classPath");
		String selectionMethod = currentElement.getAttributeValue("selection");
		Map<String, ESInfoXmlParam[]> paramters = getParametersFromElement(currentElement);
		ArrayList<String> searchMethodsIDList = getSearchMethodsIDList(currentElement);
		ArrayList<String> cutPercentagesList = getAttributesScalingFactorList(currentElement);

		dcesInfoXml.setID(id);
		dcesInfoXml.setClassName(className);
		dcesInfoXml.setParameters(paramters);
		dcesInfoXml.setSelectionMethod(selectionMethod);
		dcesInfoXml.setCutPercentagesList(cutPercentagesList);
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

	private static Map<String, ESInfoXmlParam[]> getParametersFromElement(Element element) {
		Map<String, ESInfoXmlParam[]> paramters = new HashMap<String, ESInfoXmlParam[]>();
		element = element.getFirstChildElement("parameters");
		Elements paramElements = element.getChildElements();
		// Loop on parameters elements
		for (int j = 0; j < paramElements.size(); j++) {
			Elements valuesElements = paramElements.get(j).getChildElements();
			ArrayList<ESInfoXmlParam> paramList = new ArrayList<ESInfoXmlParam>();

			String option = paramElements.get(j).getAttributeValue("option");
			String name = paramElements.get(j).getAttributeValue("name");
			String description = paramElements.get(j).getAttributeValue("description");

			// Loop on values elements
			for (int k = 0; k < valuesElements.size(); k++) {
				String useAttributeValue = valuesElements.get(k).getAttributeValue("use");
				if (useAttributeValue != null && useAttributeValue.equals("false"))
					continue;
				ESInfoXmlParam esXMLParam = new ESInfoXmlParam();
				String value = valuesElements.get(k).getValue();
				String valueStr = valuesElements.get(k).getAttributeValue("valueStr");

				esXMLParam.setDescription(description);
				esXMLParam.setName(name);
				esXMLParam.setOption(option);
				esXMLParam.setValue(value);
				esXMLParam.setValueStr(valueStr);

				paramList.add(esXMLParam);
			}
			paramters.put(option, paramList.toArray(new ESInfoXmlParam[0]));
		}
		return paramters;
	}

	private static ArrayList<String> getSearchMethodsIDList(Element element) {
		ArrayList<String> searchMethodsIDList = new ArrayList<String>();
		element = element.getFirstChildElement("searchReferences");
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

	private static ArrayList<String> getAttributesScalingFactorList(Element element) {
		ArrayList<String> attrFactorList = new ArrayList<>();
		element = element.getFirstChildElement("selected_attributes");
		if (element != null) {
			Elements factorElements = element.getChildElements();
			for (int i = 0; i < factorElements.size(); i++) {
				Element factorElement = factorElements.get(i);
				attrFactorList.add(factorElement.getValue());
			}
		}
		return attrFactorList;
	}

	public static String getLangDetectorProfiles() {
		Element element = fsEvaluatorsXmlRoot.getFirstChildElement("LanguageDetectorProfiles");
		if (element != null)
			return element.getValue();
		return "";
	}
}
