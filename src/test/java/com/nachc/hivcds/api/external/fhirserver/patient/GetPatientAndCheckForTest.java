package com.nachc.hivcds.api.external.fhirserver.patient;

import java.util.List;

import org.junit.Test;

import com.nachc.hivcds.api.hashivtest.HasHivTest;
import com.nachc.hivcds.api.hashivtest.HasHivTestResponse;
import com.nachc.hivcds.util.fhir.patient.FhirPatientFetcher;
import com.nachc.hivcds.util.props.HivCdsApiProperties;

public class GetPatientAndCheckForTest {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HasHivTest.class);	
	
	@Test
	public void shouldGetPatientAndRunCheckForTest() {
		log.info("Starting test...");
		FhirPatientFetcher fetcher = new FhirPatientFetcher();
		log.info("Fetching patient...");
		String patientId = HivCdsApiProperties.getPatientId();
		log.info("Fetching patient from FHIR Server: " + patientId);
		fetcher.fetchPatient(patientId);
		List<String> patient = fetcher.getPatient();
		log.info("Running test...");
		HasHivTestResponse hasHivTestResponse = HasHivTest.exec(patient);
		log.info("HIV tests found: " + hasHivTestResponse.getHivTestCodes().size());
		log.info("Hast hiv test: " + hasHivTestResponse.hasTest());
		log.info("Done.");
	}
	
}
