package com.dces.evaluation.featureSelection;

import java.util.ArrayList;
import java.util.Map;
import com.dces.evaluation.BuilderAbstract;
import com.dces.util.xml.DCESInfoXML;

public class FeatureSelectionFiltersBuilder extends BuilderAbstract {
	private ArrayList<DocumentFeatureSelectionFilter> featureSelectionList;

	public FeatureSelectionFiltersBuilder() {
		featureSelectionList = new ArrayList<DocumentFeatureSelectionFilter>();
	}

	public ArrayList<DocumentFeatureSelectionFilter> getFeatureSelectionFilters() {
		return featureSelectionList;
	}

	private DCESInfoXML getSeachMethodbyID(String id, ArrayList<DCESInfoXML> list) {
		for (DCESInfoXML infoXML : list) {
			if (infoXML.getID().equals(id))
				return infoXML;
		}
		return null;
	}

	public void build(ArrayList<DCESInfoXML> fsEvalXmlList,
			ArrayList<DCESInfoXML> fsSearchMethodsXmlList) {
		// Loop on all feature selection evaluators
		for (DCESInfoXML fsEvalXml : fsEvalXmlList) {
			ArrayList<Map<String, String>> currentfsEvalOptionsList = getOptions(fsEvalXml);
			ArrayList<String> seachMethodsList = fsEvalXml.getEvaluatorSearchMethodsIDList();
			ArrayList<String> attributesScalingFactorList = fsEvalXml.getCutPercentagesList();
			// Loop on all searching methods
			for (String searchMethod : seachMethodsList) {
				DCESInfoXML currentSearchMethodXML = getSeachMethodbyID(searchMethod,
						fsSearchMethodsXmlList);
				ArrayList<Map<String, String>> currentSeachMethodOptionsList = getOptions(currentSearchMethodXML);
				// Loop on all current search methods options
				for (Map<String, String> currentSeachMethodOptions : currentSeachMethodOptionsList) {
					// Loop on all current evaluator options
					for (Map<String, String> currentfsEvalOptions : currentfsEvalOptionsList)
						for (String scalingPerc : attributesScalingFactorList) {
							DocumentFeatureSelectionFilter dsF = new DocumentFeatureSelectionFilter();
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
