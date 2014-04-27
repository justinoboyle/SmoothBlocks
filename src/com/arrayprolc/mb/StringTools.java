package com.arrayprolc.mb;

public class StringTools {

	public static String convert(String s) {
		s.replace(" ", "");
		s.replace("Spawn", "");
		s.replace("BlockOf", "");
		s.replace("Block", "");
		s.replace("Log", "Wood");
		s = s.toLowerCase();
		return s;
	}

}
