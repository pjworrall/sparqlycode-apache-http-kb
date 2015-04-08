package net.interition.sparqlycode.ahkbp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.interition.sparlycode.ahkbp.model.HTTPO;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.stackify.apache.ApacheConfigParser;
import com.stackify.apache.ConfigNode;

public class ApacheHttpKBPublisher extends RDFServices {

	private final Log logger = LogFactory.getLog(ApacheHttpKBPublisher.class);

	void publishHttpKb(String filename) throws UnexpectedContentException, IOException {
		
		InputStream inputStream = new FileInputStream(filename);

		// initialise prefix
		buildPrefix();

		ApacheConfigParser parser = new ApacheConfigParser();
		ConfigNode root = parser.parse(inputStream);

		List<ConfigNode> nodes = root.getChildren();

		for (ConfigNode node : nodes) {
			DirectiveEnum directive = DirectiveEnum
					.getDirective(node.getName());

			if (directive == DirectiveEnum.VirtualHost) {

				logger.debug(node.getName() + " " + node.getContent());

				VirtualHost vhost = new VirtualHost(UriHelper.getPort(node
						.getContent()));

				constructVirtualHost(node.getChildren(), vhost);
			}
		}

		File file = new File("apache-http-kb.ttl");
		try {
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			writeRdf(model, file);
		} catch (Exception e) {
			logger.error("Error opening file or writing out RDF.", e);
		}

	}

	@SuppressWarnings("incomplete-switch")
	private void constructVirtualHost(final List<ConfigNode> nodes,
			VirtualHost vhost) throws UnexpectedContentException {

		for (ConfigNode node : nodes) {

			logger.debug("node.getName(): " + node.getName()
					+ ", node.getContent(): " + node.getContent());

			DirectiveEnum directive = DirectiveEnum
					.getDirective(node.getName());

			// Extend this switch to introduce support for other declarations.
			switch (directive) {
			case ServerName:
				vhost.setServerName(node.getContent());
				createVirtualHostStatement(vhost, node);
				break;
			case ProxyPass:
				createProxyPassStatement(vhost, node);
				break;
			}

		}

	}

	private void createVirtualHostStatement(final VirtualHost vhost,
			final ConfigNode node) {

		// create the virtualhost resource

		Resource virtualHostResource = model.createResource(
				prefix + vhost.getServerName(),
				HTTPO.VirtualHost);

		// add the name as a rdfs:label
		virtualHostResource.addProperty(RDFS.label, vhost.getServerName());

	}

	private void createProxyPassStatement(final VirtualHost vhost,
			final ConfigNode node) throws UnexpectedContentException {

		ProxyPath proxyPath = new ProxyPath(vhost, node.getContent());

		logger.debug("remoteUrl: " + proxyPath.getRemoteUrl() + ", localUrl: "
				+ proxyPath.getLocalUrl());

		// create remote resource
		Resource remoteResource = model.createResource(
				proxyPath.getRemoteUrl(), HTTPO.External);

		// create local resource
		Resource localResource = model.createResource(proxyPath.getLocalUrl(),
				HTTPO.Internal);

		// associate remote and local to vhost with bnode and web:proxy property
		// this looks like duplication but isn't: eg the resource would have
		// already been created
		// the only risk really is integrity, if the resource was created
		// differently

		Resource virtualHostResource = model.createResource(
				prefix + vhost.getServerName(),
				HTTPO.VirtualHost);

		// create an annonymous node
		Resource proxy = model.createResource();

		proxy.addProperty(HTTPO.proxy, remoteResource);
		proxy.addProperty(HTTPO.proxied, localResource);

		virtualHostResource.addProperty(HTTPO.proxyPass, proxy);

	}

}
