package com.dces.core;

import java.util.ArrayList;
import java.util.Random;
import com.dces.evaluation.EvaluationInfo;

public class DEEvaluationLog{
	private int id;
	private ArrayList<EvaluationInfo> evalInfoList;

	public DEEvaluationLog() {
		id = randomID();
		evalInfoList = new ArrayList<EvaluationInfo>();
	}

	public void updateReport(EvaluationInfo evalInfo) {
		if (evalInfo == null)
			return;
		evalInfoList.add(evalInfo);
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
