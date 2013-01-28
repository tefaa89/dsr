package com.dces.evaluation.classifiers;

import java.util.Map;
import org.slf4j.LoggerFactory;
import weka.classifiers.AbstractClassifier;
import ch.qos.logback.classic.Logger;

/**
 * Since we are not going to build the AbstractClassifier, this object will ONLY
 * hold the classifier class path and the classifier options
 *
 * @author TeFa
 *
 */
public class DocumentClassifer {
	private static Logger logger = (Logger) LoggerFactory.getLogger(DocumentClassifer.class);
	private AbstractClassifier classifier;

	public DocumentClassifer() {

	}

	public DocumentClassifer(DocumentClassifer docClassifer) {
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
			if(optionsStr.trim() != "")
				classifier.setOptions(weka.core.Utils.splitOptions(optionsStr));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return optionsStr;
	}

	public String[] getOptions() {
		return classifier.getOptions();
	}

	public String getOptionsStr() {
		StringBuffer str = new StringBuffer();
		for (String option : getOptions()) {
			str.append(option);
			str.append(" ");
		}
		return str.toString().trim();
	}

	public AbstractClassifier getClassifier() {
		return classifier;
	}

	public void setClassifier(AbstractClassifier classifier) {
		this.classifier = classifier;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DocumentClassifer))
			return false;
		DocumentClassifer docClassifier = (DocumentClassifer) obj;
		if (!getClass().equals(docClassifier.getClassPath()))
			return false;
		String[] optionsArr1 = classifier.getOptions();
		String[] optionsArr2 = docClassifier.getOptions();
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
