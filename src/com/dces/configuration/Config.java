package com.dces.configuration;

import java.io.File;
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
