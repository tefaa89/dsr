package com.esda.test;

import com.esda.configuration.Config;
import com.esda.evaluation.DEInstances;
import com.esda.evaluation.LoadDirectoryInstances;
import com.esda.evaluation.featureExtraction.FeatureSpaceGenerator;
import com.esda.evaluation.featureSelection.FeatureSelectionFilter;
import com.esda.evaluation.featureSelection.FeatureSelectionFiltersBuilder;

public class DocumentFeatureSelectionFilterTest {
	public static void main(String[] args) {
		Config.initConfiguration();
		LoadDirectoryInstances loadDI = new LoadDirectoryInstances("c:\\test",false);
		loadDI.load();

		FeatureSelectionFiltersBuilder fsfb = new FeatureSelectionFiltersBuilder();
		fsfb.build(Config.getFSEvaluatorsInfo(), Config.getFSSeachMethodInfo());

		FeatureSpaceGenerator fsGenerator = new FeatureSpaceGenerator(loadDI.getRowDataInstances());

		DEInstances featuresDeInstances;

		while (fsGenerator.hasNext()) {
			featuresDeInstances = fsGenerator.getNext();
			try {
				System.out.println(featuresDeInstances.getInstances());
				FeatureSelectionFilter fsF = fsfb.getFeatureSelectionFilters().get(0);
				/*fsF.setEvaluator("weka.attributeSelection.CfsSubsetEval");
				fsF.setSearchMethod("weka.attributeSelection.BestFirst");
				AttributeSelection filter = new AttributeSelection(); // package
																		// weka.filters.supervised.attribute!
				GreedyStepwise search = new GreedyStepwise();
				ASEvaluation asEval = (ASEvaluation) Class.forName(
						"weka.attributeSelection.CfsSubsetEval").newInstance();
				filter.setEvaluator(asEval);
				filter.setSearch(search);
				filter.setInputFormat(featuresDeInstances.getInstances());
				// generate new data
				Instances newData = Filter.useFilter(featuresDeInstances.getInstances(), filter);*/
				DEInstances data = fsF.useFilter(featuresDeInstances);
				System.out.println(data.getInstances());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
