package com.dces.evaluation;

import java.io.File;
import java.io.IOException;
import org.slf4j.LoggerFactory;
import weka.core.Instances;
import weka.core.converters.TextDirectoryLoader;
import ch.qos.logback.classic.Logger;

public class LoadDirectoryInstances {
	private static Logger logger = (Logger) LoggerFactory.getLogger(LoadDirectoryInstances.class);
	private String dataCorpusPath;
	private Instances rowdataInstances;

	public LoadDirectoryInstances(String dataCorpusPath) {
		this.dataCorpusPath = dataCorpusPath;
	}

	public void load() {
		try {
			TextDirectoryLoader loader = new TextDirectoryLoader();
			loader.setDirectory(new File(dataCorpusPath));
			rowdataInstances = loader.getDataSet();
		} catch (IOException e) {
			logger.error("Loading data corpus from directory: {}", e);
		}
	}

	public Instances getRowDataInstances() {
		return rowdataInstances;
	}
}
