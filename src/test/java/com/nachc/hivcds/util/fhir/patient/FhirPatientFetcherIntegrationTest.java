package com.nachc.hivcds.util.fhir.patient;

import org.junit.Test;

import com.nachc.hivcds.api.hashivtest.HasHivTest;

public class FhirPatientFetcherIntegrationTest {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HasHivTest.class);	
	
	private static final String patientId = "5acc8bb4-2d14-4461-a560-228d96459cc3";
	
	@Test
	public void shouldGetPatient() {
		log.info("Starting test...");
		new FhirPatientFetcher().fetchPatient(patientId);
		log.info("Done.");
	}
	
}
