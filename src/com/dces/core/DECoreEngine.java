package com.dces.core;

import com.dces.classifier.EvalClassifiers;
import com.dces.instances.DEInstances;
import com.dces.instances.EvalFeatureSpace;
import com.dces.instances.LoadDirectoryInstances;
import com.dces.util.Trace;

public class DECoreEngine {
	private DEReport report;
	private String dataCorpusPath;

	public DECoreEngine() {

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
		while (evalFS.hasNext()) {
			DEInstances featuresDeInstances = evalFS.getNextDEInstances();
			EvalClassifiers evalClass = new EvalClassifiers(featuresDeInstances);
			while (evalClass.hasNext()) {
				DEInstances classifierDeInstances = evalClass.getNextDEInstances();
				report.updateReport(classifierDeInstances.getEvaluationParameters(),
						classifierDeInstances.getEvaluationResults());
			}
		}
	}
}
