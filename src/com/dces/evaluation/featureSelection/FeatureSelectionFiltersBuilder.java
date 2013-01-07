package com.dces.evaluation.featureSelection;

import java.util.ArrayList;
import com.dces.util.FeatureSelectionInfoXml;

public class FeatureSelectionFiltersBuilder {
	private ArrayList<DocumentFeatureSelectionFilter> featureSelectionList;

	public FeatureSelectionFiltersBuilder() {

	}

	public ArrayList<DocumentFeatureSelectionFilter> getFeatureSelectionFilters() {
		return featureSelectionList;
	}

	public void build(ArrayList<FeatureSelectionInfoXml> fSIXmlList) {

	}
}
