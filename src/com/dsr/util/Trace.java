package com.dsr.util;

import com.dsr.configuration.MainConfig;

public class Trace {
	public static void trace(Object toPrint) {
		if (MainConfig.DEBUG) {
			System.out.println(toPrint.toString());
		}
	}
}
