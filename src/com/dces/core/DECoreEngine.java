package com.dces.core;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import com.dces.configuration.Config;
import com.dces.evaluation.DEInstances;
import com.dces.evaluation.LoadDirectoryInstances;
import com.dces.evaluation.classifiers.ClassifiersBuilder;
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
		fsfb.build(Config.getFeatureSelectionInfo());
		logger.info("Feature Selection Filters Built Successfuly");
		
		logger.info("Initializing Feature Space Generator");
		FeatureSpaceGenerator fsGenerator = new FeatureSpaceGenerator(loadDI.getRowDataInstances());
		
		DEInstances featuresDeInstances;
		int featureCounter = 0;
		int classifiersCounter = 0;
		FeatureSelectionEvaluator fsEval = new FeatureSelectionEvaluator();
		fsEval.setClassifierList(classifiers.getClassifiersWithDefaultSettings());
		fsEval.setFeatureSelectionFilterList(fsfb.getFeatureSelectionFilters());
		fsEval.setEvaluationLog(evalLog);
		
		while(fsGenerator.hasNext())
		{
			featuresDeInstances = fsGenerator.getNext();
			fsEval.evaluate(featuresDeInstances);
		}

		logger.debug("Num of Features Evaluated: {}", featureCounter);
		logger.debug("Num of Classifiers Evaluated: {}", classifiersCounter);
	}
}
