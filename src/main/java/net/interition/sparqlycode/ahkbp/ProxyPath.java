package net.interition.sparqlycode.ahkbp;

/*
 * 
 * http://httpd.apache.org/docs/2.4/mod/mod_proxy.html#proxypass
 * 
 * ProxyPass [path] !|url [key=value [key=value ...]] [nocanon] [interpolate] [noquery]
 * 
 * This version ignores attributes in the directive after the new url
 * 
 */

public final class ProxyPath {

	private String path;
	private String localUrl;
	private String remoteUrl;

	public ProxyPath(VirtualHost vhost, String content)
			throws UnexpectedContentException {

		processContent(content);
		
		processRemoteUrl(vhost);

	}

	private void processRemoteUrl(VirtualHost vhost) {
		// we want the scheme
		String scheme = UriHelper.getUrlScheme(vhost);
		// we want the server name
		String serverName = vhost.getServerName();
		// we want to add these to the application context
		
		remoteUrl = scheme + serverName + path ;
		
		
	}

	private void processContent(String content)
			throws UnexpectedContentException {
		// split the content according to having a status or not
		String[] col = content.split("\\s+");

		if (col.length >= 2) {
			path = col[0];
			localUrl = col[1];
		} else {
			throw new UnexpectedContentException(
					"expected to find at least two arguments in directive: "
							+ content);
		}
	}

	public String getLocalUrl() {
		return localUrl;
	}

	public String getPath() {
		return path;
	}

	public String getRemoteUrl() {
		return remoteUrl;

	}

}
