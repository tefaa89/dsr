package com.dces.util;

import com.dces.configuration.Config;
public class Trace {
	public static void trace(Object toPrint) {
		if (Config.debug()) {
			System.out.println(toPrint.toString());
		}
	}
}
