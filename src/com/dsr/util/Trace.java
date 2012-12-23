package com.dsr.util;

import com.dsr.configuration.Config;
public class Trace {
	public static void trace(Object toPrint) {
		if (Config.debug()) {
			System.out.println(toPrint.toString());
		}
	}
}
