package com.esda.evaluation;

import java.util.ArrayList;
import java.util.Map;
import org.slf4j.LoggerFactory;
import com.esda.util.xml.ESInfoXmlParam;
import weka.core.OptionHandler;
import ch.qos.logback.classic.Logger;

public abstract class ESOptionsAbstract {
	private static Logger logger = (Logger) LoggerFactory.getLogger(ESOptionsAbstract.class);

	protected String setOptions(Map<String, ESInfoXmlParam> options, OptionHandler optionHandler) {
		ArrayList<String> optionsList = new ArrayList<>();
		for (String option : options.keySet()) {
			String value = options.get(option).getValue().trim();
			if ((value.equals("") && option.equals("*")) || value.equals("*"))
				continue;
			if (option.equals("*")) {
				optionsList.add("-" + value);
			} else {
				optionsList.add("-" + option);
				optionsList.add(value);
			}
		}
		String[] optionsArr = optionsList.toArray(new String[optionsList.size()]);
		try {
			optionHandler.setOptions(optionsArr);
		} catch (Exception e) {
			logger.error("Faild to set Options.\n{}", e.toString());
		}
		return weka.core.Utils.joinOptions(optionsArr);
	}

	protected String[] getOptionsArr(OptionHandler optionHandler) {
		return optionHandler.getOptions();
	}

	protected String getOptionsStr(OptionHandler optionHandler) {
		String[] optionsArr = getOptionsArr(optionHandler);
		String res = "";
		for (String str : optionsArr)
			res += str + " ";
		return res.trim();
	}
}
