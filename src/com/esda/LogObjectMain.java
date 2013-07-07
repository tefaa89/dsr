package com.esda;

import com.esda.core.ESEvaluationLog;
import com.esda.util.FileUtil;

public class LogObjectMain {
	public static void main(String[] args) {
		String logObjPath = "resources/obj/evaluationLog.obj";
		ESEvaluationLog log = (ESEvaluationLog)FileUtil.loadObjectFromFile(logObjPath);
		System.out.println(log.getBestNEvalInfo(2));
	}
}
