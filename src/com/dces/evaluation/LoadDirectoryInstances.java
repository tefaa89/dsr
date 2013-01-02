package com.dces.evaluation;

import java.io.File;
import java.io.IOException;
import weka.core.Instances;
import weka.core.converters.TextDirectoryLoader;

public class LoadDirectoryInstances {
	private String dataCorpusPath;
	private Instances rowdataInstances;
	
	public LoadDirectoryInstances(String dataCorpusPath) {
		this.dataCorpusPath = dataCorpusPath;
	}
	
	public void load()
	{
		try {
			TextDirectoryLoader loader = new TextDirectoryLoader();
			loader.setDirectory(new File(dataCorpusPath));
			rowdataInstances = loader.getDataSet();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Instances getRowDataInstances()
	{
		return rowdataInstances;
	}
}
