package org.jbehave.osgi.services;

import java.util.List;

/**
 * <p>
 * Jbehave OSGi Embedder Service
 * </p>
 * 
 * @author Cristiano Gavião
 */
public interface EmbedderService {

	boolean isStarted();

	void showStatus();

	void runStoriesWithAnnotatedEmbedderRunner();
	
	List<String> getEmbedderClassList();
	
	void runStoriesWithAnnotatedEmbedderRunner(List<String> includes);

}
