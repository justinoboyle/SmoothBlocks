package com.arrayprolc.smoothblocks;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Grab {
	public static String s = "1";
	public static boolean a = false;

	public static boolean shouldLoad() {
		if (!a) {
			s = web();
		}
		if (s.equalsIgnoreCase("1")) {
			return true;
		} else {
			return false;
		}
	}

	public static String web() {
		String text = null;
		try {
			URL url = new URL("http://pastebin.com/raw.php?i=jPG8TiKK");
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numRead);
			}
			text = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return text;

	}

}
