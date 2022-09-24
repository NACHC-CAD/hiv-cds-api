package com.nachc.hivcds.api.hashivtest;


import org.junit.Test;

import com.nach.core.util.file.FileUtil;

public class HasHivTestIntegrationTest {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HasHivTestIntegrationTest.class);	
	
	private static final String TEST_PATIENT_FILE = "/fhir/patient/Glenn0_Hermiston71_d7e5852a-974c-6d45-f81d-5a87bbd05d2a.json";
	
	@Test
	public void shouldGetResponse() {
		log.info("Starting test...");
		String patientJson = FileUtil.getAsString(TEST_PATIENT_FILE);
		HasHivTestResponse hasHivTestResponse = HasHivTest.exec(patientJson);
		log.info("HIV tests found: " + hasHivTestResponse.getHivTestCodes().size());
		log.info("Hast hiv test: " + hasHivTestResponse.hasTest());
		log.info("Done.");
	}
	
}
