package com.dces.evaluation;

import java.util.ArrayList;
import com.dces.core.DEEvaluationLog;

public abstract class EvaluatorAbstract {
	protected ArrayList<EvaluationInfo> evaluationInfoResultList;

	public ArrayList<EvaluationInfo> getEvaluationInfoResultList() {
		return evaluationInfoResultList;
	}

	protected void clear() {
		evaluationInfoResultList = new ArrayList<EvaluationInfo>();
	}

	public synchronized void updateEvaluationLog(DEEvaluationLog evaLog) {
		if (evaluationInfoResultList == null)
			evaluationInfoResultList = new ArrayList<EvaluationInfo>();
		for (EvaluationInfo evalInfo : evaluationInfoResultList)
			evaLog.updateReport(evalInfo);
	}

	public synchronized void updateEvaluationInfo(EvaluationInfo evalInfo) {
		if (evaluationInfoResultList == null)
			evaluationInfoResultList = new ArrayList<EvaluationInfo>();
		evaluationInfoResultList.add(evalInfo);
	}
}
