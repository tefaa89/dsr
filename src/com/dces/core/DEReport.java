package com.dces.core;

import java.util.Random;
import java.util.Vector;
import com.dces.instances.EvaluationParameters;
import com.dces.instances.EvaluationResults;

public class DEReport {
	private int id;
	private Vector<EvaluationParameters> evalParamsVec;
	private Vector<EvaluationResults> evalResultsVec;

	public DEReport() {
		id = randomID();
		evalParamsVec = new Vector<EvaluationParameters>();
		evalResultsVec = new Vector<EvaluationResults>();
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
