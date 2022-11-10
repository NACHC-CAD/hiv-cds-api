package com.nachc.hivcds.util.fhir.patient;

import java.util.ArrayList;
import java.util.List;

import org.nachc.tools.fhirtoomop.fhir.parser.bundle.BundleParser;
import org.nachc.tools.fhirtoomop.fhir.parser.bundle.IBundleParser;
import org.nachc.tools.fhirtoomop.tools.download.patient.fetcher.FhirPatientEverythingFetcher;
import org.nachc.tools.fhirtoomop.tools.download.patient.fetcher.FhirPatientEverythingNextFetcher;
import org.nachc.tools.fhirtoomop.util.params.AppParams;
import org.yaorma.util.time.TimeUtil;

import com.nachc.hivcds.api.hashivtest.HasHivTest;

public class FhirPatientFetcher {

	//
	// static variables and methods
	//
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HasHivTest.class);	
	
	private static final int RETRY_MAX;;

	static {
		if(AppParams.get("downloadRetryCount") != null) {
			RETRY_MAX = Integer.parseInt(AppParams.get("downloadRetryCount"));
		} else {
			RETRY_MAX = 5;
		}
	}

	//
	// instance variables
	//
	
	private List<String> patient = new ArrayList<String>();
	
	//
	// trivial getters and setters
	//
	
	public List<String> getPatient() {
		return this.patient;
	}
	
	public void fetchPatient(String patientId) {
		FhirPatientEverythingFetcher fetcher = new FhirPatientEverythingFetcher();
		String json = fetcher.fetchEverything(patientId);
		int statusCode = fetcher.getStatusCode();
		if (statusCode != 200) {
			log.warn("DID NOT GET 200 RESPONSE: Sleeping for 3 seconds and retrying");
			TimeUtil.sleep(3);
			boolean success = false;
			int numberOfTries = 0;
			while (success == false && numberOfTries < RETRY_MAX) {
				numberOfTries++;
				fetcher = new FhirPatientEverythingFetcher();
				json = fetcher.fetchEverything(patientId);
				statusCode = fetcher.getStatusCode();
				if (statusCode == 200) {
					success = true;
				}
			}
			if(success == false) {
				log.error("Could not download patient: " + patientId);
				return;
			}
		}
		this.patient.add(json);
		log.info("Initial json added for patient");
		getNext(json, patientId, 0);
	}

	private void getNext(String json, String patientId, int numberOfAttempts) {
		IBundleParser parser;
		try {
			parser = new BundleParser(json);
		} catch(Exception exp) {
			parser = new  org.nachc.tools.fhirtoomop.fhir.parser.r4.bundle.BundleParser(json);
		}
		String nextUrl = parser.getNextUrl();
		if (nextUrl != null) {
			log.info("Getting next: " + nextUrl);
			FhirPatientEverythingNextFetcher fetcher = new FhirPatientEverythingNextFetcher();
			String nextJson = fetcher.fetchNext(nextUrl);
			int statusCode = fetcher.getStatusCode();
			if (statusCode != 200) {
				log.warn("DID NOT GET 200 RESPONSE (getting next): Sleeping for 3 seconds and retrying");
				TimeUtil.sleep(3);
				boolean success = false;
				int numberOfTries = 0;
				while (success == false && numberOfTries < RETRY_MAX) {
					numberOfTries++;
					fetcher = new FhirPatientEverythingNextFetcher();
					json = fetcher.fetchNext(patientId);
					statusCode = fetcher.getStatusCode();
					if (statusCode == 200) {
						success = true;
					}
				}
			}
			this.patient.add(nextJson);
			log.info("Page " + patient.size() + " added for patient.");
		}
	}

}
