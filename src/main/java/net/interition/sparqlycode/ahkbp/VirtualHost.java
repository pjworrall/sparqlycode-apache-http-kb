package net.interition.sparqlycode.ahkbp;

import java.util.List;

public class VirtualHost {
	
	private String port;
	private String serverName;
	private List<Redirect> redirects;
	private List<ProxyPath> proxyPaths;
	
	public VirtualHost (String port) {
		this.port = port ;
	}
	
	
	public List<Redirect> getRedirects() {
		return redirects;
	}
	
	public List<ProxyPath> getProxyPaths() {
		return proxyPaths;
	}
	
	public void add(Redirect redirect) {
		this.redirects.add(redirect);
	}
	
	public void add(ProxyPath proxyPath) {
		this.proxyPaths.add(proxyPath);
	}

	public String getServerName() {
		return serverName;
	}
	
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getPort() {
		return port;
	}

	
}
