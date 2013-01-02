package com.dces.core;

import java.util.ArrayList;
import java.util.Random;
import com.dces.evaluation.EvaluationParameters;
import com.dces.evaluation.EvaluationResults;

public class DEReport {
	private int id;
	private ArrayList<EvaluationParameters> evalParamsVec;
	private ArrayList<EvaluationResults> evalResultsVec;

	public DEReport() {
		id = randomID();
		evalParamsVec = new ArrayList<EvaluationParameters>();
		evalResultsVec = new ArrayList<EvaluationResults>();
	}

	public void updateReport(EvaluationParameters evalParams, EvaluationResults evalResults) {
		if (evalParams == null || evalResults == null)
			return;
		evalParamsVec.add(evalParams);
		evalResultsVec.add(evalResults);
	}

	public int randomID() {
		Random rand = new Random();
		int randomNum = rand.nextInt(999999) + 1;
		return randomNum;
	}

	@Override
	public String toString() {
		return "Report " + id + ":\n";
	}

}
