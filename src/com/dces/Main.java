package com.dces;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import com.dces.core.DECoreEngine;

public class Main {
	private static Logger logger = (Logger) LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		evalTextData();
	}

	public static void evalTextData()
	{
		logger.info("Initializing Core Engine:");
		DECoreEngine dsrEngine = new DECoreEngine();
		dsrEngine.setDataCorpusPath("c:\\test");
		dsrEngine.evaluate();
		logger.debug("Evaluation Log: {}", dsrEngine.getEvaluationLog());
		logger.info("Best Classifier Configutration: {}"
				, dsrEngine.getEvaluationLog().getBestNEvalInfo(2));
	}

	public static void evalArffData()
	{
		logger.info("Initializing Core Engine:");
		DECoreEngine dsrEngine = new DECoreEngine();
		dsrEngine.setDataCorpusPath("c:\\test\\data.arff");
		dsrEngine.evaluateARFF();
		logger.debug("Evaluation Log: {}", dsrEngine.getEvaluationLog());
		logger.info("Best Classifier Configutration: {}"
				, dsrEngine.getEvaluationLog().getBestNEvalInfo(3));
	}
}
