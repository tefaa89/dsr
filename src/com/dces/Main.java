package com.dces;

import com.dces.core.DECoreEngine;

public class Main {
	public static void main(String[] args) {
		DECoreEngine dsrEngine = new DECoreEngine();
		dsrEngine.setDataCorpusPath("c:\\test");
		dsrEngine.evaluate();
		System.out.println(dsrEngine.getReport());
	}
}
