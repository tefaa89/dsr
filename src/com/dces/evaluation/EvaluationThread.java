package com.dces.evaluation;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import com.dces.core.DEEvaluationLog;
import com.dces.evaluation.classifiers.ClassifiersBuilder;
import com.dces.evaluation.classifiers.ClassifiersEvaluator;
import com.dces.evaluation.featureSelection.FeatureSelectionEvaluator;
import com.dces.evaluation.featureSelection.FeatureSelectionFiltersBuilder;

public class EvaluationThread implements Runnable {

	private static Logger logger = (Logger) LoggerFactory.getLogger(EvaluationThread.class);
	private FeatureSelectionFiltersBuilder fsfb;
	private ClassifiersBuilder classifiers;
	private DEEvaluationLog evalLog;
	private DEInstances instancesToEval;
	private Thread thread;

	public EvaluationThread() {
		thread = new Thread(this);
	}

	public void start() {
		thread.start();
	}

	public void setTrainingInstances(DEInstances instancesToEval) {
		this.instancesToEval = instancesToEval;
	}

	public void setClassifierBuilder(ClassifiersBuilder classifiers) {
		this.classifiers = classifiers;
	}

	public void setFSBuilder(FeatureSelectionFiltersBuilder fsfb) {
		this.fsfb = fsfb;
	}

	public void setEvalLog(DEEvaluationLog evalLog) {
		this.evalLog = evalLog;
	}

	@Override
	public void run() {
		logger.info("Initializing Feature Selection Evaluator");
		FeatureSelectionEvaluator fsEval = new FeatureSelectionEvaluator();
		fsEval.setClassifierList(classifiers.getClassifiersWithDefaultSettings());
		fsEval.setFeatureSelectionFilterList(fsfb.getFeatureSelectionFilters());
		fsEval.clear();
		logger.info("Evaluating Feature Space on Feature Selection Methods ...");
		fsEval.evaluate(instancesToEval);
		fsEval.updateEvaluationLog(evalLog);

		logger.info("Initializing Classifiers Evaluator");
		ClassifiersEvaluator cEval = new ClassifiersEvaluator();
		cEval.setClassifiersMap(classifiers.getClassifiersExcludingDefaultSettings());
		cEval.clear();
		cEval.setFeatureSelectionFilterMap(fsEval.getBestClassifiersFSMap());
		logger.info("Evaluating Feature Space on All Classifiers ...");
		cEval.evaluate(instancesToEval);
		cEval.updateEvaluationLog(evalLog);
	}
}
