package com.esda.evaluation.featureExtraction;

import java.util.Map;
import org.slf4j.LoggerFactory;
import weka.core.Instances;
import weka.core.OptionHandler;
import weka.filters.Filter;
import ch.qos.logback.classic.Logger;
import com.esda.evaluation.ESInstances;
import com.esda.evaluation.ESOptionsAbstract;

public class FeatureExtractorFilter extends ESOptionsAbstract {
	private static Logger logger = (Logger) LoggerFactory.getLogger(FeatureExtractorFilter.class);
	private Filter filter;

	public FeatureExtractorFilter() {
	}

	public void setClassPath(String classPath) {
		try {
			filter = (Filter) Class.forName(classPath).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			logger.error(
					"Faild to initialize (FeatureExtractionFilter) Class.forName(classPath)\n{}",
					e.toString());
		}
	}

	public String getClassPath() {
		return filter.getClass().getName();
	}

	public ESInstances useFilter(ESInstances instances) {
		ESInstances filteredInstances = new ESInstances();
		if (filter == null)
			return instances;
		try {
			Filter copyFilter = Filter.makeCopy(filter);
			Instances unFilteredInstances = instances.getInstances();
			copyFilter.setInputFormat(unFilteredInstances);
			Instances filteredInstancesWeka = Filter.useFilter(unFilteredInstances, copyFilter);
			filteredInstances.setInstances(filteredInstancesWeka);
			filteredInstances.setParameters(instances.getEvaluationParameters());
			//filteredInstances.getEvaluationParameters().setFeatureSelection(this);
		} catch (Exception e) {
			logger.error("Applying filter on instances: {}", e.toString());
		}
		return filteredInstances;
	}

	public String setOptions(Map<String, String> options) {
		return setOptions(options, (OptionHandler)filter);
	}

	public String[] getOptionsArr() {
		return getOptionsArr((OptionHandler)filter);
	}

	public String getOptionsStr() {
		return  getOptionsStr((OptionHandler)filter);
	}
}
