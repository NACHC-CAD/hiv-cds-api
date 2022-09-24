package com.nachc.hivcds.api;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.nachc.tools.fhirtoomop.tools.populate.PopulateOmopInstanceFromFhirFiles;
import org.nachc.tools.fhirtoomop.util.db.connection.OmopDatabaseConnectionFactory;
import org.nachc.tools.fhirtoomop.util.db.counts.GetCountForTable;
import org.nachc.tools.fhirtoomop.util.db.truncatedatatables.TruncateAllDataTables;
import org.yaorma.util.time.Timer;

import com.googlecode.junittoolbox.SuiteClasses;
import com.googlecode.junittoolbox.WildcardPatternSuite;
import com.nachc.hivcds.api.hashivtest.HasHivTestIntegrationTest;

@RunWith(WildcardPatternSuite.class)

@SuiteClasses({ "**/*IntegrationTest.class" })

public class RunAllIntegrationTests {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HasHivTestIntegrationTest.class);

	private static Timer TIMER = new Timer();

	@BeforeClass
	public static void setup() {
		TIMER.start();
	}

	@AfterClass
	public static void cleanup() {
		TIMER.stop();
		log.info("Start:   " + TIMER.getStartAsString());
		log.info("Stop:    " + TIMER.getStopAsString());
		log.info("Elapsed: " + TIMER.getElapsedString());
		log.info("");
		log.info("Done.");
	}

	public static void exec() {
		JUnitCore junit = new JUnitCore();
		Result result = junit.run(RunAllIntegrationTests.class);
		System.out.println("Finished. Result: Failures: " + result.getFailureCount() + ". Ignored: " + result.getIgnoreCount() + ". Tests run: " + result.getRunCount() + ". Time: " + result.getRunTime() + "ms.");
	}

}
