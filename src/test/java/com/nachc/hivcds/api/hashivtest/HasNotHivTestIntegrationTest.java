package com.nachc.hivcds.api.hashivtest;


import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.nach.core.util.file.FileUtil;

public class HasNotHivTestIntegrationTest {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HasNotHivTestIntegrationTest.class);	
	
	private static final String TEST_PATIENT_FILE = "/fhir/patient/Aaron697_Little434_b8a36e7d-84c4-c766-97d0-4c9477e925f3.json";
	
	@Test
	public void shouldGetResponse() {
		log.info("Starting test...");
		String patientJson = FileUtil.getAsString(TEST_PATIENT_FILE);
		HasHivTestResponse hasHivTestResponse = HasHivTest.exec(patientJson);
		log.info("HIV tests found: " + hasHivTestResponse.getHivTestCodes().size());
		log.info("Hast hiv test: " + hasHivTestResponse.hasTest());
		assertTrue(hasHivTestResponse.getHivTestCodes().size() == 0);
		assertTrue(hasHivTestResponse.hasTest() == false);
		log.info("Done.");
	}
	
}
