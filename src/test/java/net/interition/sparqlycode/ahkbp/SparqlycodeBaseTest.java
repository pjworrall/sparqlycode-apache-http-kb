package net.interition.sparqlycode.ahkbp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 * 
 * @author Paul Worrall, Interition Ltd.
 * 
 */
public class SparqlycodeBaseTest {
	
	protected transient final Log log = LogFactory.getLog(getClass());

	protected Model model;

	private String sparqlyQueryExt = ".rq";
	private String sparqlyQueryFolder = "/sparqlycode/";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		this.model = ModelFactory.createDefaultModel();
		
		URL skb = this.getClass().getResource("/apache-http-kb.ttl");
		
		if(skb == null) throw new IOException("Apache HTTP KB not found. Have you produced it yet?");
		
		model.read(skb.toString());
		
	}
	

	private boolean ask(String query) {

 	    Query sparqlQuery = QueryFactory.create(query) ;
 	
		QueryExecution qexec = QueryExecutionFactory.create(sparqlQuery, model) ;
		boolean result = qexec.execAsk() ;
		qexec.close() ;
		
		return result;
		}
	

	private String getQuery(String name) throws Exception {
		
		String filename = sparqlyQueryFolder + name + sparqlyQueryExt;

		URL url = this.getClass().getResource(filename);
		
		if(url == null) { 
			throw new FileNotFoundException("sparql query file [" + filename + "] not found on classpath" );
		}

		String query = null ;
		FileInputStream inputStream = new FileInputStream(url.getPath());
		try {
			query = IOUtils.toString(inputStream);
		} finally {
			inputStream.close();
		}
		
		if(query == null) log.debug("SPARQL Query " + name + "not found or empty");
		
		return query;
		
	}
	
	/*
	 * the exception on this needs to be specialised to Sparqlycode
	 */
	public boolean sparqlyCodeTest(String name) throws Exception {
		String query = getQuery(name);
		return ask(query);
		
	}

}
