package com.arrayprolc.smoothblocks;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Strings extends Main {
	String verNumber;
	static String loginMotd = null;
	static boolean updatemsg = false;

	public static void shouldUpdate() {
		Bukkit.broadcastMessage("Should update");
		if (grab(connect("shouldUpdate")).equalsIgnoreCase("true")) {
			updateStrings();
			Bukkit.broadcastMessage("updateStrings()");
		}
	}

	public static String connect(String id) {
		return "http://www.arrayprolc.com/" + id + ".txt";
	}

	public static void updateStrings() {
		if (!grab(connect("version")).equalsIgnoreCase(Main.version)) {
			loginMotd = grab(connect("LoginMotd"));
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.isOp() && !updatemsg) {
					player.sendMessage(loginMotd);
					updatemsg = true;
					Bukkit.broadcastMessage("updatemsg = true");
				}
			}
		}
	}

	public static String grab(String webPage) {
		String text = null;
		try {
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			text = sb.toString();

		} catch (MalformedURLException e) {

		} catch (IOException e) {

		}
		return text;
	}

}
