package com.esda.evaluation.featureSelection;

import java.util.ArrayList;
import java.util.Map;
import com.esda.evaluation.BuilderAbstract;
import com.esda.util.xml.ESInfoXML;
import com.esda.util.xml.ESInfoXmlParam;

public class FeatureSelectionFiltersBuilder extends BuilderAbstract {
	private ArrayList<FeatureSelectionFilter> featureSelectionList;

	public FeatureSelectionFiltersBuilder() {
		featureSelectionList = new ArrayList<FeatureSelectionFilter>();
	}

	public ArrayList<FeatureSelectionFilter> getFeatureSelectionFilters() {
		return featureSelectionList;
	}

	private ESInfoXML getSeachMethodbyID(String id, ArrayList<ESInfoXML> list) {
		for (ESInfoXML infoXML : list) {
			if (infoXML.getID().equals(id))
				return infoXML;
		}
		return null;
	}

	public void build(ArrayList<ESInfoXML> fsEvalXmlList,
			ArrayList<ESInfoXML> fsSearchMethodsXmlList) {
		// Loop on all feature selection evaluators
		for (ESInfoXML fsEvalXml : fsEvalXmlList) {
			ArrayList<Map<String, ESInfoXmlParam>> currentfsEvalOptionsList = getOptions(fsEvalXml);
			ArrayList<String> seachMethodsList = fsEvalXml.getEvaluatorSearchMethodsIDList();
			ArrayList<String> attributesScalingFactorList = fsEvalXml.getCutPercentagesList();
			// Loop on all searching methods
			for (String searchMethod : seachMethodsList) {
				ESInfoXML currentSearchMethodXML = getSeachMethodbyID(searchMethod,
						fsSearchMethodsXmlList);
				ArrayList<Map<String, ESInfoXmlParam>> currentSeachMethodOptionsList = getOptions(currentSearchMethodXML);
				// Loop on all current search methods options
				for (Map<String, ESInfoXmlParam> currentSeachMethodOptions : currentSeachMethodOptionsList) {
					// Loop on all current evaluator options
					for (Map<String, ESInfoXmlParam> currentfsEvalOptions : currentfsEvalOptionsList){
						if(attributesScalingFactorList.size() == 0)
						{
							FeatureSelectionFilter dsF = new FeatureSelectionFilter();
							dsF.setEvaluator(fsEvalXml.getClassName());
							dsF.setEvaluatorOptions(currentfsEvalOptions);
							dsF.setSearchMethod(currentSearchMethodXML.getClassName());
							dsF.setSearchMethodOptions(currentSeachMethodOptions);
							featureSelectionList.add(dsF);
						}
						for (String scalingPerc : attributesScalingFactorList) {
							FeatureSelectionFilter dsF = new FeatureSelectionFilter();
							double scalingFactor = Double.parseDouble(scalingPerc.replace("%", "")) / 100.0;
							dsF.setAttibutesScalingFactor(scalingFactor);
							dsF.setEvaluator(fsEvalXml.getClassName());
							dsF.setEvaluatorOptions(currentfsEvalOptions);
							dsF.setSearchMethod(currentSearchMethodXML.getClassName());
							dsF.setSearchMethodOptions(currentSeachMethodOptions);
							featureSelectionList.add(dsF);
						}
					}

				}
			}
		}
	}
}
