package com.esda.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.esda.util.xml.ESInfoXML;
import com.esda.util.xml.ESInfoXmlParam;

public abstract class BuilderAbstract {
	protected ArrayList<Map<String, ESInfoXmlParam>> getOptions(ESInfoXML infoXML) {
		ArrayList<Map<String, ESInfoXmlParam>> optionsList = new ArrayList<Map<String, ESInfoXmlParam>>();
		Map<String, ESInfoXmlParam[]> parametersMap = infoXML.getParameters();
		String selectionType = infoXML.getSelectionMethod();
		int numOfParamters = parametersMap.size();
		if (selectionType.equals(ESInfoXML.SELECTION_LINEAR)) {
			String[] firstParamArr = (String[]) parametersMap.values().toArray()[0];
			int numValuesinEachParam = firstParamArr.length;
			for (int i = 0; i < numValuesinEachParam; i++) {
				Map<String, ESInfoXmlParam> currentOptions = new HashMap<String, ESInfoXmlParam>();
				for (String param : parametersMap.keySet()) {
					ESInfoXmlParam[] currentParamValuesArr = parametersMap.get(param);
					currentOptions.put(param, currentParamValuesArr[i]);
				}
				optionsList.add(currentOptions);
			}
		} else {
			String[] keys = parametersMap.keySet().toArray(new String[0]);
			int[] indexArr = new int[numOfParamters];
			while (true) {
				Map<String, ESInfoXmlParam> currentOptions = new HashMap<String, ESInfoXmlParam>();
				for (int i = 0; i < indexArr.length; i++)
					currentOptions.put(keys[i], parametersMap.get(keys[i])[indexArr[i]]);
				optionsList.add(currentOptions);
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
		return optionsList;
	}
}
