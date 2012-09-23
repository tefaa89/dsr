package com.dsr.util;

import java.util.ArrayList;
import java.util.Vector;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import com.dsr.instances.DocumentInstance;
import com.dsr.instances.DocumentInstances;

public class DSRWekaUtil {
	public static Instances convertDocInstancesToWekaInstances(DocumentInstances docInstances) {
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
			instanceValues = convertVectorToDoubleArray(docInstance.getFeaturesValues(),
					wekaInstances.numAttributes());
			int classIndex = docInstances.getCategoriesVec().indexOf(
					docInstance.getDocNGram().getCategory());
			/*
			 * TODO Try to find a way to indicate that an instance is
			 * uncategorized instead of giving a random class to it.
			 */
			instanceValues[wekaInstances.numAttributes() - 1] = classIndex < 0 ? 0 : classIndex;
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
