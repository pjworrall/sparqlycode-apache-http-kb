package net.interition.sparqlycode.ahkbp;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProxyPathTest {

	@Test
	public void test() {
		
		VirtualHost vhost = new VirtualHost("443");
		
		vhost.setServerName("www.sparqlycode.com");
		
		ProxyPath proxyPath;
		
		try {
			proxyPath = new ProxyPath(vhost,"/konakartadmin ajp://localhost:8788/konakartadmin");
			
			assertEquals(proxyPath.getPath(), "/konakartadmin");
			assertEquals(proxyPath.getLocalUrl(), "ajp://localhost:8788/konakartadmin");
			assertEquals(proxyPath.getRemoteUrl(), "https://www.sparqlycode.com/konakartadmin");
			
		} catch (UnexpectedContentException e) {
			fail(e.getMessage());
		}
		
	}

}
