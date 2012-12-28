package com.dces;

import com.dces.configuration.Config;
import com.dces.core.DECoreEngine;

public class Main {
	public static void main(String[] args) {
		System.out.println("MySQL JDBC Driver Registered!");
		Config.initConfiguration();
		DECoreEngine dsrEngine = new DECoreEngine();
		dsrEngine.setDataCorpusPath("c:\\test");
		dsrEngine.evaluate();
		System.out.println(dsrEngine.getReport());
	}
}
