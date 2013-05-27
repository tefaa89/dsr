package com.esda.evaluation.featureExtraction;

import java.util.Map;
import org.slf4j.LoggerFactory;
import weka.core.OptionHandler;
import weka.filters.Filter;
import ch.qos.logback.classic.Logger;

public class FeatureExtractorFilter {
	private static Logger logger = (Logger) LoggerFactory.getLogger(FeatureExtractorFilter.class);
	private Filter filter;

	public FeatureExtractorFilter() {
		// TODO Auto-generated constructor stub
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

	public String setOptions(Map<String, String> options) {
		String optionsStr = "";
		for (String option : options.keySet()) {
			String value = options.get(option);
			if (value.equals("") && option.equals("*"))
				continue;
			if (option.equals("*")) {
				optionsStr += "-" + options.get(option) + " ";
			} else
				optionsStr += "-" + option + " " + options.get(option) + " ";
		}
		try {
			if (optionsStr.trim() != "")
				((OptionHandler) filter).setOptions(weka.core.Utils.splitOptions(optionsStr));
		} catch (Exception e) {
			logger.error("Faild to set Options.\n{}", e.toString());
		}
		return optionsStr;
	}
}
