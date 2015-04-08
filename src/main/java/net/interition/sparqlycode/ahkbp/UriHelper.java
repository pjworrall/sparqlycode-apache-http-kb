package net.interition.sparqlycode.ahkbp;

public final class UriHelper {

	public static String getUrlScheme(final VirtualHost vhost) {

		String resource = null;

		if (vhost.getPort().equals("80")) {
			resource = "http://";
		} else if (vhost.getPort().equals("443")) {
			resource = "https://";
		} else {
			resource = "http:" + vhost.getPort() + "//";
		}

		return resource;

	}

	public static String getPort(String content) {

		// this could have an index oo bounds exceptions!

		String[] parts = content.split(":");
		String port = parts[parts.length - 1];

		return port;

	}
}
