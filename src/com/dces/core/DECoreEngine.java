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
		int featureCounter = 0;
		int classifiersCounter = 0;
		while (evalFS.hasNext()) {
			featuresDeInstances = evalFS.getNextDEInstances();
			featureCounter++;
			EvalClassifiers evalClass = new EvalClassifiers(featuresDeInstances);
			while (evalClass.hasNext()) {
				// It will get the same instance with updated report
				DEInstances classifierDeInstances = evalClass.getNext();
				System.out.println(classifierDeInstances);
				report.updateReport(classifierDeInstances.getEvaluationParameters(),
						classifierDeInstances.getEvaluationResults());
				classifiersCounter++;
			}
		}
		
		Trace.trace("Num of Features Evaluated: " + featureCounter);
		Trace.trace("Num of Classifiers Evaluated: " + classifiersCounter);
	}
}
