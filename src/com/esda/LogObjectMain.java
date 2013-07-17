package com.esda;

import java.util.ArrayList;
import com.esda.core.ESEvaluationLog;
import com.esda.evaluation.EvaluationInfo;
import com.esda.util.FileUtil;
import com.websocket.ESWebSocket;

public class LogObjectMain {
	public static void main(String[] args) {
		ESWebSocket.wst.init();
		String logObjPath = "evaluationLog_RES.obj";
		ESEvaluationLog log = (ESEvaluationLog)FileUtil.loadObjectFromFile(logObjPath);

		try {
			System.out.println("OPEN CLIENT");
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.remove(74);
		log.remove(103);
		log.remove(132);
		log.remove(110);
		log.remove(129);

		ArrayList<EvaluationInfo> evalInfoList = log.getBestNEvalInfo(10);
		for(EvaluationInfo evalInfo:evalInfoList)
			ESWebSocket.updateClassifierAcc(evalInfo);
		System.out.println(evalInfoList);
	}
}

//7076