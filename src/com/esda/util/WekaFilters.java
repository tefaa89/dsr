package com.esda.util;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Standardize;
import com.esda.evaluation.DEInstances;

public class WekaFilters {
	public static DEInstances standardizeFilter(DEInstances instances)
	{
		try {
			Standardize filter = new Standardize();
			filter.setInputFormat(instances.getInstances());
			Instances filteredData = Filter.useFilter(instances.getInstances(), filter);
			instances.setInstances(filteredData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instances;
	}
}
