package com.dces.evaluation;

import weka.classifiers.Evaluation;

public class EvaluationResults {
	private Evaluation evaluation;

	public EvaluationResults() {

	}

	public EvaluationResults(EvaluationResults evalRes) {
		this.evaluation = evalRes.getEvaluation();
	}

	public double getAccuracy() {
		return evaluation.correct();
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	@Override
	public String toString() {
		String matrix = "";
		try {
			matrix = evaluation.toMatrixString();
		} catch (Exception e) {

		}
		return "# Evaluation Results: \n\t Summary: \n" + matrix.replaceAll("\n", "\n\t\t")
				+ "\n\t" + evaluation.toSummaryString().replaceAll("\n", "\n\t\t");
	}
}
