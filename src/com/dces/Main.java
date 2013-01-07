package com.dces;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import com.dces.core.DECoreEngine;

public class Main {
	private static Logger logger = (Logger)LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		logger.info("Initializing Core Engine:");
		DECoreEngine dsrEngine = new DECoreEngine();
		dsrEngine.setDataCorpusPath("c:\\test");
		dsrEngine.evaluate();
		System.out.println(dsrEngine.getEvaluationLog());
	}
}
