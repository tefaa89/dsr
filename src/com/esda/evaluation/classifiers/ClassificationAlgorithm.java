package com.esda.evaluation.classifiers;

import java.util.Map;
import org.slf4j.LoggerFactory;
import weka.classifiers.AbstractClassifier;
import ch.qos.logback.classic.Logger;
import com.esda.evaluation.ESOptionsAbstract;
import com.esda.util.xml.ESInfoXmlParam;

/**
 * Since we are not going to build the AbstractClassifier, this object will ONLY
 * hold the classifier class path and the classifier options
 *
 * @author TeFa
 *
 */
public class ClassificationAlgorithm extends ESOptionsAbstract {
	private static Logger logger = (Logger) LoggerFactory.getLogger(ClassificationAlgorithm.class);
	private AbstractClassifier classifier;

	public ClassificationAlgorithm() {

	}

	public ClassificationAlgorithm(ClassificationAlgorithm docClassifer) {
		this.classifier = docClassifer.getClassifier();
	}

	public String getClassPath() {
		return classifier.getClass().getName();
	}

	public void setClassPath(String classPath) {
		try {
			classifier = (AbstractClassifier) Class.forName(classPath).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			logger.error("Faild to initialize (AbstractClassifier) Class.forName(classPath)\n{}",
					e.toString());
		}
	}

	public AbstractClassifier getClassifier() {
		return classifier;
	}

	public void setClassifier(AbstractClassifier classifier) {
		this.classifier = classifier;
	}

	public String setOptions(Map<String, ESInfoXmlParam> options) {
		return setOptions(options, classifier);
	}

	public String[] getOptionsArr() {
		return getOptionsArr(classifier);
	}

	public String getOptionsStr() {
		return getOptionsStr(classifier);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ClassificationAlgorithm))
			return false;
		ClassificationAlgorithm docClassifier = (ClassificationAlgorithm) obj;
		if (!getClass().equals(docClassifier.getClassPath()))
			return false;
		String[] optionsArr1 = classifier.getOptions();
		String[] optionsArr2 = docClassifier.getOptionsArr();
		if (optionsArr1.length != optionsArr2.length)
			return false;
		for (int i = 0; i < optionsArr1.length; i++)
			if (!optionsArr1[i].equals(optionsArr2[i]))
				return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("Classifier Info:\n\t");
		str.append("Path: ");
		str.append(classifier.getClass().getName());
		str.append("\n\t");
		str.append("Parameters: ");
		str.append(getOptionsStr());

		return str.toString();
	}
}
