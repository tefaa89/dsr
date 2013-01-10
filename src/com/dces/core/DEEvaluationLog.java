package com.dces.core;

import java.util.ArrayList;
import java.util.Random;
import com.dces.evaluation.EvaluationInfo;

public class DEEvaluationLog {
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
	
	public EvaluationInfo getBestEvaluationInfo()
	{
		EvaluationInfo res = evalInfoList.remove(0);
		for (EvaluationInfo evalInfo : evalInfoList) {
			double currentAccuracy = evalInfo.getEvalResults().getAccuracy();
			double prevAccuracy = res.getEvalResults().getAccuracy();
			if (currentAccuracy > prevAccuracy)
				res = evalInfo;
		}
		return res;
	}
	
	@Override
	public String toString() {
		String res = "";
		for (EvaluationInfo evalInfo : evalInfoList)
			res += evalInfo.toString();
		return "\nReport " + id + ":\n" + res;
	}

}
