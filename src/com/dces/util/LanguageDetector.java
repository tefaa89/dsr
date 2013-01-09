package com.dces.util;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.dces.configuration.Config;
import com.dces.util.enumu.LanguageEnum;

public class LanguageDetector {
	public LanguageDetector() {
		try {
			if(DetectorFactory.getLangList().size() <= 0)
				DetectorFactory.loadProfile(Config.getLangDetectorProfiles());
		} catch (LangDetectException e) {
			e.printStackTrace();
		}
	}

	public LanguageEnum detectLang(String docContent) {
		try {
			Detector det = DetectorFactory.create();
			det.append(docContent);
			String lang = det.detect();
			switch (lang) {
			case "en":
				return LanguageEnum.English;

			case "de":
				return LanguageEnum.German;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return LanguageEnum.German;
	}
}
