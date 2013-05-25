package com.dces;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import com.dces.core.DECoreEngine;
import com.websocket.DCESWebSocket;

public class Main {
	private static Logger logger = (Logger) LoggerFactory.getLogger(Main.class);
	public static DECoreEngine dsrEngine;

	public static void main(String[] args) {
		DCESWebSocket.wst.init();
		evalTextData();
	}

	public static void evalTextData() {
		logger.info("Initializing Core Engine:");
		dsrEngine = new DECoreEngine();
		dsrEngine.setDataCorpusPath("test");
		/*dsrEngine.evaluate();
		printInfo();*/
	}

	public static void evalArffData() {
		logger.info("Initializing Core Engine:");
		dsrEngine = new DECoreEngine();
		dsrEngine.setDataCorpusPath("test\\data.arff");
		/*dsrEngine.evaluateARFF();
		printInfo();*/
	}


	public static void printInfo() {
		logger.debug("Evaluation Log: {}", dsrEngine.getEvaluationLog());
		logger.info("Best Classifier Configutration: {}", dsrEngine.getEvaluationLog()
				.getBestNEvalInfo(3));
	}
}
