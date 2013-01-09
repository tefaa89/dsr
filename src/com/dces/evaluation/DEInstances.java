package com.dces.evaluation;

import weka.core.Instances;

public class DEInstances {
	private Instances instances;
	private EvaluationParameters evalParameters;

	public DEInstances() {
		evalParameters = new EvaluationParameters();
	}

	public DEInstances(Instances instances) {
		this();
		this.instances = instances;
	}

	public Instances getInstances() {
		return instances;
	}

	public void setInstances(Instances instances) {
		this.instances = instances;
	}

	public EvaluationParameters getEvaluationParameters() {
		return evalParameters;
	}

	public void setParameters(EvaluationParameters evalParameters) {
		this.evalParameters = evalParameters;
	}

	@Override
	public String toString() {
		return evalParameters.toString();
	}
}
