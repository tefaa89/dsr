package com.dces.evaluation.featureExtraction;

import org.slf4j.LoggerFactory;
import weka.core.Instances;
import ch.qos.logback.classic.Logger;
import com.dces.evaluation.DEInstances;
import com.dces.util.enumu.FeaturesModelEnum;
import com.dces.util.enumu.FeaturesParametersEnum;
import com.dces.util.enumu.LanguageEnum;

/**
 * This class generates all possible feature space models for a given data set
 *
 * @author TeFa
 *
 */
public class FeatureSpaceGenerator {
	private static Logger logger = (Logger) LoggerFactory.getLogger(FeatureSpaceGenerator.class);
	private FeatureSpaceBuilder fsb;
	private int featureParamIndexState;
	private int featureModelIndexState;
	private Instances rawData;
	private LanguageEnum stemmingLang;

	public FeatureSpaceGenerator(Instances rawData) {
		this.rawData = rawData;
		stemmingLang = LanguageEnum.English;
		reSetStates();
	}

	public void setStemmingLang(LanguageEnum lang) {
		stemmingLang = lang;
	}

	public DEInstances getNext() {
		if (!hasNext())
			return null;
		nextState();
		FeaturesParametersEnum currentFParam = FeaturesParametersEnum.values()[featureParamIndexState];
		FeaturesModelEnum currentFModel = FeaturesModelEnum.values()[featureModelIndexState];
		logger.debug(
				"\nFeature Space Evalutation :\n\t Param State: {}({}) \n\t Model State: {}({})",
				new Object[] { currentFParam, featureParamIndexState, currentFModel, featureModelIndexState});
		fsb = new FeatureSpaceBuilder(rawData);
		fsb.setStemmerStopListLanguage(stemmingLang);
		fsb.setFeatureSpaceModel(currentFModel);
		fsb.setFeatureSpaceParamater(currentFParam);
		fsb.setIDFTransform(true);
		fsb.setTFTransform(true);
		fsb.build();
		return fsb.getFilteredDataSet();
	}
	
	public boolean hasNext() {

		return featureParamIndexState + 1 >= FeaturesParametersEnum.values().length
				&& featureModelIndexState + 1 >= FeaturesModelEnum.values().length ? false : true;
	}

	public void reSetStates() {
		featureModelIndexState = -1;
		featureParamIndexState = 0;
	}

	private void nextState() {
		featureModelIndexState++;
		if (featureModelIndexState >= FeaturesModelEnum.values().length) {
			featureModelIndexState = 0;
			featureParamIndexState++;
		}
	}
}
