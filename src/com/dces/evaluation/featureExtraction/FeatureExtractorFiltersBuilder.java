package com.dces.evaluation.featureExtraction;

import java.util.ArrayList;
import java.util.Map;
import com.dces.evaluation.BuilderAbstract;
import com.dces.util.xml.DCESInfoXML;

public class FeatureExtractorFiltersBuilder extends BuilderAbstract {
	//private static Logger logger = (Logger) LoggerFactory.getLogger(FeatureExtractionBuilder.class);
	private ArrayList<FeatureExtractorFilter> feFiltersList;

	public FeatureExtractorFiltersBuilder() {
		feFiltersList = new ArrayList<>();
	}

	public ArrayList<FeatureExtractorFilter> getExtractionFiltersList() {
		return feFiltersList;
	}

	public void build(ArrayList<DCESInfoXML> feFiltersXmlList) {
		for (DCESInfoXML feinfoXml : feFiltersXmlList) {
			ArrayList<Map<String, String>> currentfeFilterOptionsList = getOptions(feinfoXml);
			String feFilterClassPath = feinfoXml.getClassName();
			for (Map<String, String> currentfeFilterOption : currentfeFilterOptionsList) {
				FeatureExtractorFilter feFilter = new FeatureExtractorFilter();
				feFilter.setClassPath(feFilterClassPath);
				feFilter.setOptions(currentfeFilterOption);
				feFiltersList.add(feFilter);
			}
		}
	}
}
