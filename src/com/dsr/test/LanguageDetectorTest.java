package com.dsr.test;

import com.dsr.util.LanguageDetector;

public class LanguageDetectorTest 
{
	public static void main(String[] args) {
		LanguageDetector langD = new LanguageDetector();
		System.out.println(langD.detectLang("eine Zusatzanmeldung brauchen"));
		System.out.println(langD.detectLang("Guess which language is this ?"));
	}
}
