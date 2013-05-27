package com.esda.evaluation;

import java.io.File;
import org.slf4j.LoggerFactory;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.converters.TextDirectoryLoader;
import ch.qos.logback.classic.Logger;

public class LoadDirectoryInstances {
	private static Logger logger = (Logger) LoggerFactory.getLogger(LoadDirectoryInstances.class);
	private String dataCorpusPath;
	private Instances rowdataInstances;
	private boolean isArffFile;

	public LoadDirectoryInstances(String dataCorpusPath, boolean isArffFile) {
		this.dataCorpusPath = dataCorpusPath;
		this.isArffFile = isArffFile;
	}

	public void load() {
		try {
			if (isArffFile) {
				DataSource source = new DataSource(dataCorpusPath);
				rowdataInstances = source.getDataSet();
				rowdataInstances.setClassIndex(rowdataInstances.numAttributes()-1);
			} else {
				TextDirectoryLoader loader = new TextDirectoryLoader();
				loader.setDirectory(new File(dataCorpusPath));
				rowdataInstances = loader.getDataSet();
			}
		} catch (Exception e) {
			logger.error("Loading data corpus from directory: {}", e);
			System.exit(0);
		}
	}

	public Instances getRowDataInstances() {
		return rowdataInstances;
	}
}
