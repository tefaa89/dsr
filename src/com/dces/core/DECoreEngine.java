package com.dces.core;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import com.dces.configuration.Config;
import com.dces.evaluation.DEInstances;
import com.dces.evaluation.LoadDirectoryInstances;
import com.dces.evaluation.classifiers.ClassifiersBuilder;
import com.dces.evaluation.classifiers.ClassifiersEvaluator;
import com.dces.evaluation.featureExtraction.FeatureSpaceGenerator;
import com.dces.evaluation.featureSelection.FeatureSelectionEvaluator;
import com.dces.evaluation.featureSelection.FeatureSelectionFiltersBuilder;

public class DECoreEngine {
	private static Logger logger = (Logger) LoggerFactory.getLogger(DECoreEngine.class);
	private DEEvaluationLog evalLog;
	private String dataCorpusPath;

	public DECoreEngine() {
		Config.initConfiguration();
	}

	public void setDataCorpusPath(String path) {
		dataCorpusPath = path;
	}

	public DEEvaluationLog getEvaluationLog() {
		return evalLog;
	}

	public void evaluate() {
		logger.info("Loading Corpus Data from: {}", dataCorpusPath);
		LoadDirectoryInstances loadDI = new LoadDirectoryInstances(dataCorpusPath);
		loadDI.load();
		logger.info("Corpus Data Loaded Successfuly");
		evalLog = new DEEvaluationLog();
		
		logger.info("Building Classifiers Object from XML");
		ClassifiersBuilder classifiers = new ClassifiersBuilder();
		classifiers.build(Config.getClassifiersInfo());
		logger.info("Classifiers Object Built Successfuly");
		
		logger.info("Building Feature Selection Filters from XML");
		FeatureSelectionFiltersBuilder fsfb = new FeatureSelectionFiltersBuilder();
		fsfb.build(Config.getFSEvaluatorsInfo(),Config.getFSSeachMethodInfo());
		logger.info("Feature Selection Filters Built Successfuly");
		
		logger.info("Initializing Feature Space Generator");
		FeatureSpaceGenerator fsGenerator = new FeatureSpaceGenerator(loadDI.getRowDataInstances());
		
		DEInstances featuresDeInstances;
		int featureCounter = 0;
		int classifiersCounter = 0;
		logger.info("Initializing Feature Selection Evaluator");
		FeatureSelectionEvaluator fsEval = new FeatureSelectionEvaluator();
		fsEval.setClassifierList(classifiers.getClassifiersWithDefaultSettings());
		fsEval.setFeatureSelectionFilterList(fsfb.getFeatureSelectionFilters());
		
		logger.info("Initializing Classifiers Evaluator");
		ClassifiersEvaluator cEval = new ClassifiersEvaluator();
		cEval.setClassifierList(classifiers.getClassifiersExcludingDefaultSettings());

		while (fsGenerator.hasNext()) {
			logger.info("Generating Feature Space ...");
			featuresDeInstances = fsGenerator.getNext();

			logger.info("Evaluating Feature Space on Feature Selection Methods ...");
			fsEval.evaluate(featuresDeInstances);
			fsEval.updateEvaluationLog(evalLog);

			cEval.setFeatureSelectionFilterMap(fsEval.getEvaluationInfoResultList());
			logger.info("Evaluating Feature Space on All Classifiers ...");
			cEval.evaluate(featuresDeInstances);
			cEval.updateEvaluationLog(evalLog);
		}

		logger.debug("Num of Features Evaluated: {}", featureCounter);
		logger.debug("Num of Classifiers Evaluated: {}", classifiersCounter);
	}
}
