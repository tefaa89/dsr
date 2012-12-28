package com.dces.instances;

import com.dces.util.enumu.FeaturesModelEnum;

public class EvaluationParameters {
	private FeaturesModelEnum featureModelEnum;
	private boolean tfBool;
	private boolean idfBool;
	private boolean useStopList;
	private boolean useStemming;

	public FeaturesModelEnum getFeatureModelEnum() {
		return featureModelEnum;
	}

	public void setFeatureModelEnum(FeaturesModelEnum featureModelEnum) {
		this.featureModelEnum = featureModelEnum;
	}

	public boolean isTfBool() {
		return tfBool;
	}

	public void setTfBool(boolean tfBool) {
		this.tfBool = tfBool;
	}

	public boolean isIdfBool() {
		return idfBool;
	}

	public void setIdfBool(boolean idfBool) {
		this.idfBool = idfBool;
	}

	public boolean isUseStopList() {
		return useStopList;
	}

	public void setUseStopList(boolean useStopList) {
		this.useStopList = useStopList;
	}

	public boolean isUseStemming() {
		return useStemming;
	}

	public void setUseStemming(boolean useStemming) {
		this.useStemming = useStemming;
	}

}
