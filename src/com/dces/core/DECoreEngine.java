package com.dces.core;

import com.dces.configuration.Config;
import com.dces.evaluation.DEInstances;
import com.dces.evaluation.LoadDirectoryInstances;
import com.dces.evaluation.classifiers.EvalClassifiers;
import com.dces.evaluation.features.EvalFeatureSpace;
import com.dces.util.Trace;

public class DECoreEngine {
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
		Trace.trace("Loading Corpus Data from: " + dataCorpusPath);
		LoadDirectoryInstances loadDI = new LoadDirectoryInstances(dataCorpusPath);
		loadDI.load();
		Trace.trace("Corpus Data Loaded Successfuly\n");
		EvalFeatureSpace evalFS = new EvalFeatureSpace(loadDI.getRowDataInstances());
		report = new DEReport();
		DEInstances featuresDeInstances;
		while (evalFS.hasNext()) {
			featuresDeInstances = evalFS.getNextDEInstances();
			
			EvalClassifiers evalClass = new EvalClassifiers(featuresDeInstances);
			while (evalClass.hasNext()) {
				// It will get the same instance with updated report
				DEInstances classifierDeInstances = evalClass.getNext();
				report.updateReport(classifierDeInstances.getEvaluationParameters(),
						classifierDeInstances.getEvaluationResults());
			}
		}
	}
}
