package com.dces.instances;

import weka.core.Instances;
import com.dces.util.Trace;
import com.dces.util.enumu.FeaturesModelEnum;
import com.dces.util.enumu.FeaturesParametersEnum;
import com.dces.util.enumu.LanguageEnum;

/**
 * This class generates all possible feature space models for a given data set
 * 
 * @author TeFa
 * 
 */
public class EvalFeatureSpace {

	private FeatureSpaceBuilder fsb;
	private int featureParamIndexState;
	private int featureModelIndexState;
	private Instances rawData;
	private LanguageEnum stemmingLang;

	public EvalFeatureSpace(Instances rawData) {
		this.rawData = rawData;
		stemmingLang = LanguageEnum.English;
		reSetStates();
	}

	public void setStemmingLang(LanguageEnum lang) {
		stemmingLang = lang;
	}

	public DEInstances getNextDEInstances() {
		if (!hasNext())
			return null;
		Trace.trace("Feature Space Evalutation :\n" + "\t Param State: " + featureParamIndexState
				+ "\n\t Model State: " + featureModelIndexState + "\n");
		FeaturesParametersEnum currentFParam = FeaturesParametersEnum.values()[featureParamIndexState];
		FeaturesModelEnum currentFModel = FeaturesModelEnum.values()[featureModelIndexState];
		fsb = new FeatureSpaceBuilder(rawData);
		fsb.setStemmerStopListLanguage(stemmingLang);
		fsb.setFeatureSpaceModel(currentFModel);
		fsb.setFeatureSpaceParamater(currentFParam);
		fsb.setIDFTransform(true);
		fsb.setTFTransform(true);
		nextState();
		return fsb.getFilteredDataSet();
	}

	public boolean hasNext() {
		return featureParamIndexState >= FeaturesParametersEnum.values().length ? false : true;
	}

	public void reSetStates() {
		featureParamIndexState = 0;
		featureModelIndexState = 0;
	}

	private void nextState() {
		featureModelIndexState++;
		if (featureModelIndexState >= FeaturesModelEnum.values().length) {
			featureModelIndexState = 0;
			featureParamIndexState++;
		}
	}
}
