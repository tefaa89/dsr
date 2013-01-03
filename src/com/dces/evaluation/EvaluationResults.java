package com.dces.evaluation;

public class EvaluationResults {
	private double [][] confusionMatrix;
	private String summaryString;
	
	public EvaluationResults() {
		
	}

	public double[][] getConfusionMatrix() {
		return confusionMatrix;
	}

	public void setConfusionMatrix(double[][] confusionMatrix) {
		this.confusionMatrix = confusionMatrix;
	}

	public String getSummaryString() {
		return summaryString;
	}

	public void setSummaryString(String summaryString) {
		this.summaryString = summaryString.replaceAll("\n", "\n\t\t");
	}

	@Override
	public String toString()
	{
		return "# Evaluation Results: \n\t Summary: " + summaryString;
	}
}
