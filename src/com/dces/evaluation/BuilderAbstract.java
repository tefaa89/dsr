package com.dces.evaluation;

import java.util.ArrayList;
import java.util.Map;
import com.dces.util.xml.DCESInfoXML;

public abstract class BuilderAbstract {
	protected ArrayList<Map<String, String>> getOptions(DCESInfoXML infoXML) {
		ArrayList<Map<String, String>> optionsList = new ArrayList<Map<String, String>>();
		return optionsList;
	}
}
