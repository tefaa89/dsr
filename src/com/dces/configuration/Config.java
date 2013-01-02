package com.dces.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.dces.util.ClassifierInfoXML;
import nu.xom.*;

public class Config {
	private static Element root;

	public static void initConfiguration() {
		Builder builder = new Builder();
		try {
			File file = new File("resources//configuration//config.xml");
			Document doc = builder.build(file);
			root = doc.getRootElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ClassifierInfoXML getClassifierByID(String id) {
		return getClassifierByID(id,root);
	}
	
	private static ClassifierInfoXML getClassifierByID(String id, Element currentElement)
	{
		ClassifierInfoXML classifierInfoXml = new ClassifierInfoXML();
		if(currentElement.getAttribute("id") == null)
		{
			Element element = root.getFirstChildElement("classifiers");
			if(element == null) return null;
			Elements elements = element.getChildElements();
			// Loop on classifiers elements
			for (int i = 0; i < elements.size(); i++) {
				String idSearch = elements.get(i).getAttributeValue("id");
				if(idSearch.equals(id))
				{
					currentElement = elements.get(i);
					break;
				}
			}
		}
		if(currentElement.getAttribute("id") == null) return null;
		String className = currentElement.getAttributeValue("className");
		Map<String, String[]> paramters = new HashMap<String, String[]>();
		
		Element element = currentElement.getFirstChildElement("parameters");
		Elements paramElements = element.getChildElements();
		// Loop on parameters elements
		for (int j = 0; j < paramElements.size(); j++) {
			Elements valuesElements = paramElements.get(j).getChildElements();
			String paramOption = paramElements.get(j).getAttributeValue("option");
			String[] values = new String[valuesElements.size()];
			//Loop on values elements
			for (int k = 0; k < valuesElements.size(); k++)
				values[k] = valuesElements.get(k).getValue();
			paramters.put(paramOption, values);
		}
		classifierInfoXml.setId(id);
		classifierInfoXml.setClassName(className);
		classifierInfoXml.setParameters(paramters);
		return classifierInfoXml;
	}
	
	public static ArrayList<ClassifierInfoXML> getClassifiersInfo() {
		ArrayList<ClassifierInfoXML> classifiersInfoList = new ArrayList<ClassifierInfoXML>();
		Element element = root.getFirstChildElement("classifiers");
		if (element != null) {
			Elements elements = element.getChildElements();
			// Loop on classifiers elements
			for (int i = 0; i < elements.size(); i++) {
				String id = elements.get(i).getAttributeValue("id");
				classifiersInfoList.add(getClassifierByID(id,elements.get(i)));
			}
		}
		return classifiersInfoList;
	}

	public static String getMySqlURL() {
		Element element = root.getFirstChildElement("database");
		if (element != null) {
			element = element.getFirstChildElement("mysql_url");
			if (element != null)
				return element.getValue();
		}
		return "";
	}

	public static String getDBUsername() {
		Element element = root.getFirstChildElement("database");
		if (element != null) {
			element = element.getFirstChildElement("username");
			if (element != null)
				return element.getValue();
		}
		return "";
	}

	public static String getDBPassword() {
		Element element = root.getFirstChildElement("database");
		if (element != null) {
			element = element.getFirstChildElement("password");
			if (element != null)
				return element.getValue();
		}
		return "";
	}

	public static String getLangDetectorProfiles() {
		Element element = root.getFirstChildElement("LanguageDetectorProfiles");
		if (element != null)
			return element.getValue();
		return "";
	}

	public static String getOCRServiceURL() {
		Element element = root.getFirstChildElement("webservices");
		if (element != null) {
			Elements elements = element.getChildElements();
			for (int i = 0; i < elements.size(); i++) {
				if (elements.get(i).getAttribute("name").getValue().equals("ocr"))
					return elements.get(i).getFirstChildElement("url").getValue();
			}
		}
		return "";
	}

	public static String getOCRSingleFileFunc() {
		Element element = root.getFirstChildElement("webservices");
		if (element != null) {
			Elements elements = element.getChildElements();
			for (int i = 0; i < elements.size(); i++) {
				if (elements.get(i).getAttribute("name").getValue().equals("ocr")) {
					elements = elements.get(i).getFirstChildElement("functions").getChildElements();
					for (int j = 0; j < elements.size(); j++)
						if (elements.get(j).getAttribute("name").getValue().equals("SingleFile"))
							return elements.get(j).getValue();
				}
			}
		}
		return "";
	}

	public static boolean debug() {
		Element element = root.getFirstChildElement("debug");
		if (element != null)
			return element.getValue().equals("true") ? true : false;
		return false;
	}
}
