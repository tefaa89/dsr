package com.dces.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.dces.util.xml.DCESInfoXML;

public abstract class BuilderAbstract {
	protected ArrayList<Map<String, String>> getOptions(DCESInfoXML infoXML) {
		ArrayList<Map<String, String>> optionsList = new ArrayList<Map<String, String>>();
		Map<String, String[]> parametersMap = infoXML.getParameters();
		int numOfParamters = parametersMap.size();
		String[] keys = parametersMap.keySet().toArray(new String[0]);
		int[] indexArr = new int[numOfParamters];
		while (true) {
			Map<String, String> currentOptions = new HashMap<String, String>();
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

		return optionsList;
	}
}
