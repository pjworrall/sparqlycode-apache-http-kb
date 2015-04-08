package net.interition.sparqlycode.ahkbp;

import static org.junit.Assert.*;

import org.junit.Test;

public class RedirectTest {

	@Test
	public void testRedirect() {
		
		Redirect redirect;
		
		// try with three parts to the redirect assignment
		
		try {
			redirect = new Redirect("permanent / https://www.zonafide.net/");
			assertEquals(redirect.getStatus(), "permanent");
			assertEquals(redirect.getOldUrl(), "/");
			assertEquals(redirect.getNewUrl(), "https://www.zonafide.net/");
			
		} catch (UnexpectedContentException e) {
			fail(e.getMessage());
		}
		
		
		// try with two parts to the redirect assignment
		
		try {
			redirect = new Redirect("/ https://www.zonafide.net/");
			assertEquals(redirect.getOldUrl(), "/");
			assertEquals(redirect.getNewUrl(), "https://www.zonafide.net/");
			
		} catch (UnexpectedContentException e) {
			fail(e.getMessage());
		}

	}

}
