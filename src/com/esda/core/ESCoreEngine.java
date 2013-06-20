package com.esda.core;

import java.util.ArrayList;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import com.esda.configuration.Config;
import com.esda.evaluation.ESInstances;
import com.esda.evaluation.EvaluationThread;
import com.esda.evaluation.LoadDirectoryInstances;
import com.esda.evaluation.classifiers.ClassifiersBuilder;
import com.esda.evaluation.classifiers.ClassifiersEvaluator;
import com.esda.evaluation.featureExtraction.FeatureExtractorFiltersBuilder;
import com.esda.evaluation.featureExtraction.FeatureSpaceGenerator;
import com.esda.evaluation.featureSelection.FeatureSelectionEvaluator;
import com.esda.evaluation.featureSelection.FeatureSelectionFiltersBuilder;
import com.esda.util.FileUtil;
import com.esda.util.WekaFilters;

public class ESCoreEngine {
	private static Logger logger = (Logger) LoggerFactory.getLogger(ESCoreEngine.class);
	private ESEvaluationLog evalLog;
	private String dataCorpusPath;

	public ESCoreEngine() {
		Config.initConfiguration();
	}

	public void setDataCorpusPath(String path) {
		dataCorpusPath = path;
	}

	public ESEvaluationLog getEvaluationLog() {
		return evalLog;
	}

	public void evaluateARFF() {
		logger.info("Loading Corpus Data from: {}", dataCorpusPath);
		LoadDirectoryInstances loadDI = new LoadDirectoryInstances(dataCorpusPath, true);
		loadDI.load();
		logger.info("Corpus Data Loaded Successfuly");

		ESInstances instances = new ESInstances(loadDI.getRowDataInstances());
		WekaFilters.standardizeFilter(instances);

		evalLog = new ESEvaluationLog();
		logger.info("Building Classifiers Object from XML");
		ClassifiersBuilder classifiers = new ClassifiersBuilder();
		classifiers.build(Config.getClassifiersInfo());
		logger.info("Classifiers Object Built Successfuly");

		logger.info("Building Feature Selection Filters from XML");
		FeatureSelectionFiltersBuilder fsfb = new FeatureSelectionFiltersBuilder();
		fsfb.build(Config.getFSEvaluatorsInfo(), Config.getFSSeachMethodInfo());
		logger.info("Feature Selection Filters Built Successfuly");

		logger.info("Initializing Feature Selection Evaluator");
		FeatureSelectionEvaluator fsEval = new FeatureSelectionEvaluator();
		fsEval.setClassifierList(classifiers.getClassifiersWithDefaultSettings());
		fsEval.setFeatureSelectionFilterList(fsfb.getFeatureSelectionFilters());
		fsEval.clear();
		logger.info("Evaluating Feature Space on Feature Selection Methods ...");
		fsEval.evaluate(instances);
		fsEval.updateEvaluationLog(evalLog);

		logger.info("Initializing Classifiers Evaluator");
		ClassifiersEvaluator cEval = new ClassifiersEvaluator();
		cEval.setClassifiersMap(classifiers.getClassifiersExcludingDefaultSettings());
		cEval.clear();
		cEval.setFeatureSelectionFilterMap(fsEval.getBestClassifiersFSMap());
		logger.info("Evaluating Feature Space on All Classifiers ...");
		cEval.evaluate(instances);
		cEval.updateEvaluationLog(evalLog);
	}

	public void evaluate() {
		logger.info("Loading Corpus Data from: {}", dataCorpusPath);
		LoadDirectoryInstances loadDI = new LoadDirectoryInstances(dataCorpusPath, false);
		loadDI.load();
		logger.info("Corpus Data Loaded Successfuly");
		evalLog = new ESEvaluationLog();

		logger.info("Building Feature Extraction Filters from XML");
		FeatureExtractorFiltersBuilder fefb = new FeatureExtractorFiltersBuilder();
		fefb.build(Config.getFEMethodsInfo());
		logger.info("Feature Extraction Filters Built Successfuly");

		logger.info("Building Feature Selection Filters from XML");
		FeatureSelectionFiltersBuilder fsfb = new FeatureSelectionFiltersBuilder();
		fsfb.build(Config.getFSEvaluatorsInfo(), Config.getFSSeachMethodInfo());
		logger.info("Feature Selection Filters Built Successfuly");

		logger.info("Building Classifiers Object from XML");
		ClassifiersBuilder classifiers = new ClassifiersBuilder();
		classifiers.build(Config.getClassifiersInfo());
		logger.info("Classifiers Object Built Successfuly");

		logger.info("Initializing Feature Space Generator");
		FeatureSpaceGenerator fsGenerator = new FeatureSpaceGenerator();
		fsGenerator.setRawInstances(loadDI.getRowDataInstances());
		fsGenerator.setExtractionFiltersList(fefb.getExtractionFiltersList());

		fefb.getExtractionFiltersList();

		ESInstances featuresDeInstances;
		int featureCounter = 0;
		int classifiersCounter = 0;

		ArrayList<Thread> threads = new ArrayList<>();
		while (fsGenerator.hasNext()) {
			featureCounter++;
			logger.info("Generating Feature Space ({})", featureCounter);
			featuresDeInstances = fsGenerator.getNext();
			logger.trace("Generated Feature Vector :\n{} ", featuresDeInstances.getInstances());

			EvaluationThread evalThread = new EvaluationThread();
			threads.add(evalThread.getThread());
			evalThread.setTrainingInstances(featuresDeInstances);
			evalThread.setClassifierBuilder(classifiers);
			evalThread.setFSBuilder(fsfb);
			evalThread.setEvalLog(evalLog);
			logger.debug("Starting Evaluation Thread ");
			evalThread.start();
		}
		waitForEvaluationThreads(threads);
		serializeLogInfo();
		printInfo();
		logger.debug("Num of Features Evaluated: {}", featureCounter);
		logger.debug("Num of Classifiers Evaluated: {}", classifiersCounter);
		logger.info("Evaluation Finished Successfully ...");
	}

	private void serializeLogInfo() {
		FileUtil.writeObjectToFile(evalLog, "resources/obj/evaluationLog.obj");
	}

	private void waitForEvaluationThreads(ArrayList<Thread> threads) {
		for (Thread thread : threads)
			try {
				thread.join();
			} catch (InterruptedException e) {
				logger.error("Thread Interruption Error");
			}
	}

	public void printInfo() {
		logger.debug("Evaluation Log: {}", getEvaluationLog());
		logger.info("Best Classifier Configutration: {}", getEvaluationLog()
				.getBestNEvalInfo(3));
	}
}
