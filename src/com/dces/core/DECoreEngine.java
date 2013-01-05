package com.dces.core;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import com.dces.configuration.Config;
import com.dces.evaluation.DEInstances;
import com.dces.evaluation.LoadDirectoryInstances;
import com.dces.evaluation.classifiers.EvalClassifiers;
import com.dces.evaluation.features.FeatureSpaceGenerator;

public class DECoreEngine {
	private static Logger logger = (Logger) LoggerFactory.getLogger(DECoreEngine.class);
	private DEReport report;
	private String dataCorpusPath;

	public DECoreEngine() {
		Config.initConfiguration();
	}

	public void setDataCorpusPath(String path) {
		dataCorpusPath = path;
	}

	public DEReport getReport() {
		return report;
	}

	public void evaluate() {
		logger.info("Loading Corpus Data from: {}", dataCorpusPath);
		LoadDirectoryInstances loadDI = new LoadDirectoryInstances(dataCorpusPath);
		loadDI.load();
		logger.info("Corpus Data Loaded Successfuly");
		FeatureSpaceGenerator evalFS = new FeatureSpaceGenerator(loadDI.getRowDataInstances());
		report = new DEReport();
		DEInstances featuresDeInstances;
		int featureCounter = 0;
		int classifiersCounter = 0;
		while (evalFS.hasNext()) {
			featuresDeInstances = evalFS.getNextDEInstances();

			featureCounter++;
			EvalClassifiers evalClass = new EvalClassifiers(featuresDeInstances);
			while (evalClass.hasNext()) {
				// It will get the same instance with updated report
				DEInstances classifierDeInstances = evalClass.getNext();
				logger.trace("Classifier Instance: \n\t{}", classifierDeInstances);
				report.updateReport(classifierDeInstances.getEvaluationParameters(),
						classifierDeInstances.getEvaluationResults());
				classifiersCounter++;
			}
		}

		logger.debug("Num of Features Evaluated: {}", featureCounter);
		logger.debug("Num of Classifiers Evaluated: {}", classifiersCounter);
	}
}
