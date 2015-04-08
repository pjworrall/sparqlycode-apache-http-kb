package net.interition.sparqlycode.ahkbp;

/*
 * 
 * http://httpd.apache.org/docs/2.4/mod/mod_alias.html#redirect
 * 
 * Redirect [status] URL-path URL
 * 
 */

public final class Redirect {
	private  String status = "not declared";
	private  String oldUrl;
	private  String newUrl;
	
	public Redirect(String content) throws UnexpectedContentException {
		// split the content according to having a status or not
		String[] col = content.split("\\s+");
		
		switch(col.length) {
			case 3:
				status = col[0];
				oldUrl = col[1];
				newUrl = col[2];
				break;
			case 2:
				oldUrl = col[0];
				newUrl = col[1];
				break;
			default:
				throw new UnexpectedContentException("Unexpected value for the Redirect decleration: " + content);
		}
		
		
	}

	public String getStatus() {
		return status;
	}

	public String getOldUrl() {
		return oldUrl;
	}

	public String getNewUrl() {
		return newUrl;
	}
	
	
}
