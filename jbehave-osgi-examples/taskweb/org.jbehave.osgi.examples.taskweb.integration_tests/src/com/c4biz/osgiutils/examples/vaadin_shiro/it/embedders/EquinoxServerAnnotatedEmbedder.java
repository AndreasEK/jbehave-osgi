package com.c4biz.osgiutils.examples.vaadin_shiro.it.embedders;

import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;
import static org.jbehave.core.reporters.Format.XML;

import java.text.SimpleDateFormat;
import java.util.List;

import org.jbehave.core.InjectableEmbedder;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.UsingSteps;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.failures.RethrowingFailure;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.AnnotatedEmbedderRunner;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.steps.ParameterConverters.DateConverter;
import org.jbehave.osgi.configuration.OsgiConfiguration;
import org.jbehave.osgi.io.OsgiStoryFinder;
import org.jbehave.osgi.reporters.OsgiStoryReporterBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.c4biz.osgiutils.examples.vaadin_shiro.it.embedders.EquinoxServerAnnotatedEmbedder.MyDateConverter;
import com.c4biz.osgiutils.examples.vaadin_shiro.it.embedders.EquinoxServerAnnotatedEmbedder.MyOsgiConfiguration;
import com.c4biz.osgiutils.examples.vaadin_shiro.it.embedders.EquinoxServerAnnotatedEmbedder.MyEmbedder;
import com.c4biz.osgiutils.examples.vaadin_shiro.it.embedders.EquinoxServerAnnotatedEmbedder.MyRegexPrefixCapturingPatternParser;
import com.c4biz.osgiutils.examples.vaadin_shiro.it.embedders.EquinoxServerAnnotatedEmbedder.MyReportBuilder;
import com.c4biz.osgiutils.examples.vaadin_shiro.it.embedders.EquinoxServerAnnotatedEmbedder.MyStoryControls;
import com.c4biz.osgiutils.examples.vaadin_shiro.it.embedders.EquinoxServerAnnotatedEmbedder.MyStoryLoader;
import com.c4biz.osgiutils.examples.vaadin_shiro.it.steps.EquinoxVerificationSteps;

@RunWith(AnnotatedEmbedderRunner.class)
@Configure(using = MyOsgiConfiguration.class, stepPatternParser = MyRegexPrefixCapturingPatternParser.class, storyControls = MyStoryControls.class, storyLoader = MyStoryLoader.class, storyReporterBuilder = MyReportBuilder.class, parameterConverters = { MyDateConverter.class })
@UsingEmbedder(embedder = MyEmbedder.class, generateViewAfterStories = true, verboseFailures=true, ignoreFailureInStories = false, ignoreFailureInView = true, storyTimeoutInSecs = 100, threads = 1, metaFilters = "-skip")
@UsingSteps(instances = { EquinoxVerificationSteps.class})
public class EquinoxServerAnnotatedEmbedder extends InjectableEmbedder {

	@Test
	public void run() {
		List<String> storyPaths = new OsgiStoryFinder().findPaths(
				"/stories/server_product", "*.story", "");
		injectedEmbedder().runStoriesAsPaths(storyPaths);
	}

	public static class MyEmbedder extends Embedder {
		public MyEmbedder() {
		}
	}

	public static class MyOsgiConfiguration extends OsgiConfiguration {
		public MyOsgiConfiguration() {
			useFailureStrategy(new RethrowingFailure());
			usePendingStepStrategy(new FailingUponPendingStep());
			
		}
	}

	public static class MyStoryControls extends StoryControls {
		public MyStoryControls() {
			doDryRun(false);
			doSkipScenariosAfterFailure(false);
		}
	}

	public static class MyStoryLoader extends LoadFromClasspath {
		public MyStoryLoader() {
			super();
		}
	}

	public static class MyReportBuilder extends OsgiStoryReporterBuilder {
		public MyReportBuilder() {
			this.withFormats(CONSOLE, TXT, HTML, XML).withDefaultFormats();
		}
	}

	public static class MyRegexPrefixCapturingPatternParser extends
			RegexPrefixCapturingPatternParser {
		public MyRegexPrefixCapturingPatternParser() {
			super("%");
		}
	}

	public static class MyDateConverter extends DateConverter {
		public MyDateConverter() {
			super(new SimpleDateFormat("yyyy-MM-dd"));
		}
	}

}
