package com.esda.evaluation.featureExtraction;

import java.util.ArrayList;
import org.slf4j.LoggerFactory;
import weka.core.Instances;
import ch.qos.logback.classic.Logger;
import com.esda.evaluation.ESInstances;

/**
 * This class generates all possible feature space models for a given data set
 *
 * @author TeFa
 *
 */
public class FeatureSpaceGenerator {
	private static Logger logger = (Logger) LoggerFactory.getLogger(FeatureSpaceGenerator.class);
	private ArrayList<FeatureExtractorFilter> feFiltersList;
	private Instances rawInstances;

	public FeatureSpaceGenerator() {

	}

	public void setExtractionFiltersList(ArrayList<FeatureExtractorFilter> feFiltersList) {
		this.feFiltersList = feFiltersList;
	}

	public void setRawInstances(Instances rawData) {
		rawInstances = rawData;
	}

	public boolean hasNext() {
		return feFiltersList.size() != 0;
	}
	
	public ESInstances getNext() {
		if (!hasNext())
			return null;
		logger.debug("Calling: getNext():ESInstances method");
		ESInstances instances = new ESInstances();
		instances.setInstances(rawInstances);
		instances = feFiltersList.remove(0).useFilter(instances);
		return instances;
	}
}
