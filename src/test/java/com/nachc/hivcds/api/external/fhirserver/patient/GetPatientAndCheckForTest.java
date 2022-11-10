package com.nachc.hivcds.api.external.fhirserver.patient;

import java.util.List;

import org.junit.Test;

import com.nachc.hivcds.api.hashivtest.HasHivTest;
import com.nachc.hivcds.api.hashivtest.HasHivTestResponse;
import com.nachc.hivcds.util.fhir.patient.FhirPatientFetcher;

public class GetPatientAndCheckForTest {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HasHivTest.class);	
	
	// synthea
	// private static final String patientId = "5acc8bb4-2d14-4461-a560-228d96459cc3";

	private static final String patientId = "87a339d0-8cae-418e-89c7-8651e6aab3c6";
	
	@Test
	public void shouldGetPatientAndRunCheckForTest() {
		log.info("Starting test...");
		FhirPatientFetcher fetcher = new FhirPatientFetcher();
		log.info("Fetching patient...");
		fetcher.fetchPatient(patientId);
		List<String> patient = fetcher.getPatient();
		log.info("Running test...");
		HasHivTestResponse hasHivTestResponse = HasHivTest.exec(patient);
		log.info("HIV tests found: " + hasHivTestResponse.getHivTestCodes().size());
		log.info("Hast hiv test: " + hasHivTestResponse.hasTest());
		log.info("Done.");
	}
	
}
