package net.interition.sparqlycode.ahkbp;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Cli {

	private static final Log log = LogFactory.getLog(Cli.class);

	public static void main(String[] args) {

		ApacheHttpKBPublisher kbp = new ApacheHttpKBPublisher();
		

		if (args.length <1) {
			System.out.println("usage: Cli <Apache http conf file>");
			System.exit(1);
		}

		try {
			kbp.publishHttpKb(args[0]);
		} catch (UnexpectedContentException e) {
			log.error("parsing error of the Apache http configuration file", e);
		} catch (IOException e) {
			log.error("error opening the Apache http configuration file", e);
		}

	}

}
