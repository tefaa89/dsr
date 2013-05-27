package com.esda.evaluation;

import java.util.ArrayList;
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

	public ArrayList<String> getAttributesList()
	{
		ArrayList<String> attList = new ArrayList<>();
		if(instances == null)
			return attList;
		for(int i=0; i<instances.numAttributes();i++)
		{
			String attName = instances.attribute(i).name();
			attList.add(attName);
		}
		return attList;
	}

	@Override
	public String toString() {
		return evalParameters.toString();
	}
}
