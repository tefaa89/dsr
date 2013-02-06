package com.dces.evaluation;

import weka.classifiers.Evaluation;

public class EvaluationResults {
	private transient Evaluation evaluation;
	private String cMatrixStr;
	private String numOfCorrectClassInstancesStr;
	private String numOfIncorrectClassInstancesStr;
	private String accuracyStr;
	private String evaluationStr;

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
		try {
			cMatrixStr = evaluation.toMatrixString();
			accuracyStr = String.format("%.3f", evaluation.pctCorrect());
			numOfCorrectClassInstancesStr = evaluation.correct() + "";
			numOfIncorrectClassInstancesStr = evaluation.incorrect() + "";
			evaluationStr = evaluation.toSummaryString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getcMatrixStr() {
		return cMatrixStr;
	}


	public String getAccuracyStr() {
		return accuracyStr;
	}


	public String getEvaluationStr() {
		return evaluationStr;
	}

	public String getNumOfCorrectClassInstancesStr() {
		return numOfCorrectClassInstancesStr;
	}

	public String getNumOfIncorrectClassInstancesStr() {
		return numOfIncorrectClassInstancesStr;
	}

	@Override
	public String toString() {
		return "# Evaluation Results: \n\t Summary: \n" + cMatrixStr.replaceAll("\n", "\n\t\t")
				+ "\n\t" + evaluation.toSummaryString().replaceAll("\n", "\n\t\t");
	}
}
