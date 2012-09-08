package com.dsr.util;

import java.util.ArrayList;
import java.util.Vector;
import com.dsr.instances.DocumentInstance;
import com.dsr.instances.DocumentInstances;
import com.dsr.util.enumu.FeatureValuesEnum;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class DSRWekaUtil {
	public static Instances convertDocInstancesToWekaInstances(DocumentInstances docInstances,
			FeatureValuesEnum valuesType) {
		Vector<String> features = docInstances.getFeatures();
		ArrayList<Attribute> atts = new ArrayList<Attribute>();
		ArrayList<String> classVal = new ArrayList<String>();

		// Adding Class Names
		for (String className : docInstances.getCategoriesVec())
			classVal.add(className);

		// Adding Features Name
		for (String feature : features)
			atts.add(new Attribute(feature));
		atts.add(new Attribute("@@class@@", classVal));

		// Adding document instances
		Instances wekaInstances = new Instances("DSR Instances", atts, 0);
		wekaInstances.setClassIndex(wekaInstances.numAttributes() - 1);
		for (DocumentInstance docInstance : docInstances) {
			double[] instanceValues;
			if (valuesType == FeatureValuesEnum.TF_VALUES)
				instanceValues = convertVectorToDoubleArray(docInstance.getFeaturesTFValues(),
						wekaInstances.numAttributes());
			else
				instanceValues = convertVectorToDoubleArray(
						docInstance.getFeaturesTFIDFValuesVec(), wekaInstances.numAttributes());
			int classIndex = docInstances.getCategoriesVec().indexOf(docInstance.getDocNGram().getCategory());
			instanceValues[wekaInstances.numAttributes() - 1] = classIndex;

			wekaInstances.add(new DenseInstance(1.0, instanceValues));
		}
		return wekaInstances;
	}

	private static double[] convertVectorToDoubleArray(Vector<?> vec, int arraySize) {
		double[] res = new double[arraySize];
		for (int i = 0; i < vec.size(); i++)
			res[i] = (Double) vec.get(i);
		return res;
	}
}
