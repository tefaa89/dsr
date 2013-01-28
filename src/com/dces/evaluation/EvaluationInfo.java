package com.dces.evaluation;

public class EvaluationInfo {
	private EvaluationParameters evalParameters;
	private EvaluationResults evalResults;

	public EvaluationInfo() {

	}

	public EvaluationInfo(EvaluationInfo evalInfo) {
		this.evalParameters = new EvaluationParameters(evalInfo.getEvalParameters());
		this.evalResults = new EvaluationResults(evalInfo.getEvalResults());
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

	@Override
	public String toString() {
		String res = "\n============== Evaluation Info ==============\n";
		res += evalParameters + "\n";
		res += evalResults + "\n";
		return res;
	}
}
