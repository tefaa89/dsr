package com.dsr.util;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.dsr.configuration.Config;
import com.dsr.util.enumu.LanguageEnum;

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
				return LanguageEnum.ENGLISH;

			case "de":
				return LanguageEnum.GERMAN;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return LanguageEnum.GERMAN;
	}
}
