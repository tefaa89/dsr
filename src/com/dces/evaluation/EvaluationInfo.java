package com.dces.evaluation;

public class EvaluationInfo {
	private transient static int currentID = -1;
	private int id;
	private EvaluationParameters evalParameters;
	private EvaluationResults evalResults;

	public EvaluationInfo() {
		setId();
	}

	public EvaluationInfo(EvaluationInfo evalInfo) {
		setId();
		this.evalParameters = new EvaluationParameters(evalInfo.getEvalParameters());
		this.evalResults = new EvaluationResults(evalInfo.getEvalResults());
	}

	private void setId() {
		currentID++;
		id = currentID;
	}

	public int getId() {
		return id;
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
		String res = "\n============== Evaluation Info " + id + " ==============\n";
		res += evalParameters + "\n";
		res += evalResults + "\n";
		return res;
	}
}
