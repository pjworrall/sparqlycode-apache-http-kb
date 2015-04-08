package net.interition.sparqlycode.ahkbp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import net.interition.sparlycode.ahkbp.model.HTTPO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.vocabulary.RDFS;

public abstract class RDFServices {

	private final Log logger = LogFactory.getLog(RDFServices.class);

	// create an empty Jena Model
	protected Model model = ModelFactory.createDefaultModel();

	protected String prefix = "http://www.interition.net/apache/http/id/";

	/**
	 * 
	 * Writes out the RDF as Turtle
	 * 
	 * @param model
	 * @throws Exception
	 */
	protected void writeRdf(Model model, File out) throws Exception {

		logger.debug("Writing out " + model.size() + " statements.");

		try {

			FileWriter fw = new FileWriter(out.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			model.write(bw, "TURTLE");
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("Error writing out turtle file ");
		}
	}

	/**
	 * 
	 * Builds the prefix's
	 * 
	 * @return
	 */
	protected void buildPrefix() {

		// not using any default prefixes to make sure we can merge easily
		// model.setNsPrefix("", "http://default/?");

		// general prefix setting for Ontologies
		// model.setNsPrefix("foaf", FOAF.getURI());
		model.setNsPrefix("web", HTTPO.getURI());
		model.setNsPrefix("rdfs", RDFS.getURI());
		
		model.setNsPrefix("id", prefix);

		logger.debug("base uri for individuals: " + prefix);

	}
}
