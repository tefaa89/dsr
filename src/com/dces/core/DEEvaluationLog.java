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

	public EvaluationInfo getBestEvaluationInfo() {
		return getBestEvaluationInfo(evalInfoList);
	}

	private EvaluationInfo getBestEvaluationInfo(ArrayList<EvaluationInfo> evalInfoList) {
		ArrayList<EvaluationInfo> evalInfoCopyList = new ArrayList<>(evalInfoList);
		EvaluationInfo res = evalInfoCopyList.remove(0);
		for (EvaluationInfo evalInfo : evalInfoCopyList) {
			double currentAccuracy = evalInfo.getEvalResults().getAccuracy();
			double prevAccuracy = res.getEvalResults().getAccuracy();
			if (currentAccuracy > prevAccuracy)
				res = evalInfo;
			else if (currentAccuracy == prevAccuracy) {
				int currentNumOfAttr = evalInfo.getEvalParameters().getSelectedAttributes().size();
				int prevNumOfAttr = res.getEvalParameters().getSelectedAttributes().size();
				if (currentNumOfAttr < prevNumOfAttr)
					res = evalInfo;
			}
		}
		return res;
	}

	public ArrayList<EvaluationInfo> getBestNEvalInfo(int n) {
		ArrayList<EvaluationInfo> res = new ArrayList<>();
		ArrayList<EvaluationInfo> evalInfoCopyList = new ArrayList<>(evalInfoList);

		for (; n > 0 && evalInfoCopyList.size() > 0; n--) {
			EvaluationInfo currentBestEvalInfo = getBestEvaluationInfo(evalInfoCopyList);
			evalInfoCopyList.remove(currentBestEvalInfo);
			res.add(currentBestEvalInfo);
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
