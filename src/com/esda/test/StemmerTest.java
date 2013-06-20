package com.esda.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import weka.core.stemmers.SnowballStemmer;
import weka.core.tokenizers.NGramTokenizer;
import com.esda.configuration.Config;
import com.esda.evaluation.ESInstances;
import com.esda.evaluation.LoadDirectoryInstances;
import com.esda.evaluation.featureExtraction.FeatureExtractorFilter;
import com.esda.util.StringToWordVector;
import com.esda.util.xml.ESInfoXML;

public class StemmerTest {
	public static void main(String[] args) {
		Config.initConfiguration();
		LoadDirectoryInstances loadDI = new LoadDirectoryInstances("test", false);
		loadDI.load();

		ArrayList<ESInfoXML> esconfigList = Config.getFEMethodsInfo();
		ESInfoXML feinfoXml = esconfigList.get(0);

		ArrayList<Map<String, String>> currentfeFilterOptionsList = getOptions(feinfoXml);
		String feFilterClassPath = feinfoXml.getClassName();

		Map<String, String> currentfeFilterOption = currentfeFilterOptionsList.get(0);
		FeatureExtractorFilter feFilter = new FeatureExtractorFilter();
		feFilter.setClassPath(feFilterClassPath);
		feFilter.setOptions(currentfeFilterOption);

		// Second Filter
		FeatureExtractorFilter fe = new FeatureExtractorFilter();
		StringToWordVector filter = new StringToWordVector();
		fe.setFilter(filter);
		try {
			NGramTokenizer tokenizer = new NGramTokenizer();
			tokenizer.setNGramMinSize(1);
			tokenizer.setNGramMaxSize(2);

			SnowballStemmer stemmer = new SnowballStemmer();
			stemmer.setStemmer("english");

			filter.setTokenizer(tokenizer);
			filter.setUseStoplist(true);
			filter.setInputFormat(loadDI.getRowDataInstances());
			filter.setStemmer(stemmer);
			filter.setIDFTransform(true);
			filter.setTFTransform(true);
			filter.setLowerCaseTokens(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// Applying Filter on raw instances

		ESInstances instances = new ESInstances();
		instances.setInstances(loadDI.getRowDataInstances());
		instances = feFilter.useFilter(instances);
	}

	public static ArrayList<Map<String, String>> getOptions(ESInfoXML infoXML) {
		ArrayList<Map<String, String>> optionsList = new ArrayList<Map<String, String>>();
		Map<String, String[]> parametersMap = infoXML.getParameters();
		String selectionType = infoXML.getSelectionMethod();
		int numOfParamters = parametersMap.size();
		if (selectionType.equals(ESInfoXML.SELECTION_LINEAR)) {
			String[] firstParamArr = (String[]) parametersMap.values().toArray()[0];
			int numValuesinEachParam = firstParamArr.length;
			for (int i = 0; i < numValuesinEachParam; i++) {
				Map<String, String> currentOptions = new HashMap<String, String>();
				for (String param : parametersMap.keySet()) {
					String[] currentParamValuesArr = parametersMap.get(param);
					currentOptions.put(param, currentParamValuesArr[i]);
				}
				optionsList.add(currentOptions);
			}
		} else {
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
		}
		return optionsList;
	}
}
