package com.esda.evaluation.featureExtraction;

import java.util.ArrayList;
import java.util.Map;
import com.esda.evaluation.BuilderAbstract;
import com.esda.util.xml.ESInfoXML;
import com.esda.util.xml.ESInfoXmlParam;

public class FeatureExtractorFiltersBuilder extends BuilderAbstract {
	//private static Logger logger = (Logger) LoggerFactory.getLogger(FeatureExtractionBuilder.class);
	private ArrayList<FeatureExtractorFilter> feFiltersList;

	public FeatureExtractorFiltersBuilder() {
		feFiltersList = new ArrayList<>();
	}

	public ArrayList<FeatureExtractorFilter> getExtractionFiltersList() {
		return feFiltersList;
	}

	public void build(ArrayList<ESInfoXML> feFiltersXmlList) {
		for (ESInfoXML feinfoXml : feFiltersXmlList) {
			ArrayList<Map<String, ESInfoXmlParam>> currentfeFilterOptionsList = getOptions(feinfoXml);
			String feFilterClassPath = feinfoXml.getClassName();
			for (Map<String, ESInfoXmlParam> currentfeFilterOption : currentfeFilterOptionsList) {
				FeatureExtractorFilter feFilter = new FeatureExtractorFilter();
				feFilter.setClassPath(feFilterClassPath);
				feFilter.setOptions(currentfeFilterOption);
				feFilter.setParamInfoMap(currentfeFilterOption);
				feFiltersList.add(feFilter);
			}
		}
	}
}
