package com.dces.evaluation;

import weka.classifiers.Evaluation;

public class EvaluationResults {
	private Evaluation evaluation;

	public EvaluationResults() {

	}

	public double getAccuracy() {
		return evaluation.correct();
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	@Override
	public String toString() {
		return "# Evaluation Results: \n\t Summary: "
				+ evaluation.toSummaryString().replaceAll("\n", "\n\t\t");
	}
}
