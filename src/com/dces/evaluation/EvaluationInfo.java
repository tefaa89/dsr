package com.dces.evaluation;

public class EvaluationInfo {
	private EvaluationParameters evalParameters;
	private EvaluationResults evalResults;

	public EvaluationInfo() {

	}

	public EvaluationParameters getEvalParameters() {
		return evalParameters;
	}

	public void setEvalParameters(EvaluationParameters evalParameters) {
		this.evalParameters = evalParameters;
	}

	public EvaluationResults getEvalResults() {
		return evalResults;
	}

	public void setEvalResults(EvaluationResults evalResults) {
		this.evalResults = evalResults;
	}
}
