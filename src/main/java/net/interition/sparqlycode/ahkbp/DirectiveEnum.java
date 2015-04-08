package net.interition.sparqlycode.ahkbp;

public enum DirectiveEnum {
	VirtualHost, ServerName, Redirect, ProxyPass, ServerAlias, UNKNOWN;
	
	public static DirectiveEnum getDirective(String directive) {
		
		for( DirectiveEnum constant : DirectiveEnum.values() ) {
			if(directive.equalsIgnoreCase(constant.name())) return constant ;
		}
		
		return DirectiveEnum.UNKNOWN ;
	}
}
