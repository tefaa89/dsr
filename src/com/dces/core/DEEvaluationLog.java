package com.dces.core;

import java.util.ArrayList;
import java.util.Random;
import com.dces.evaluation.EvaluationInfo;
import com.dces.evaluation.EvaluationParameters;
import com.dces.evaluation.EvaluationResults;
import com.dces.evaluation.classifiers.IClassifierResults;

public class DEEvaluationLog implements IClassifierResults{
	private int id;
	private ArrayList<EvaluationInfo> evalInfoList;

	public DEEvaluationLog() {
		id = randomID();
		evalInfoList = new ArrayList<EvaluationInfo>();
	}

	public void updateReport(EvaluationParameters evalParams, EvaluationResults evalResults) {
		if (evalParams == null || evalResults == null)
			return;
		evalInfoList.add(evalParams);
		evalInfoList.add(evalResults);
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
