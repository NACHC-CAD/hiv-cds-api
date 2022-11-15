package com.nachc.hivcds.external.upload.parse;

import java.io.File;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.junit.Test;
import org.nachc.tools.fhirtoomop.fhir.parser.r4.bundle.BundleParser;

import com.nach.core.util.fhir.parser.FhirJsonParser;
import com.nach.core.util.file.FileUtil;
import com.nach.core.util.json.JsonUtil;
import com.nachc.hivcds.api.hashivtest.HasHivTestIntegrationTest;

public class GetPatientResource {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HasHivTestIntegrationTest.class);	
	
	private static final String TEST_PATIENT_FILE = "/fhir/patient/Glenn0_Hermiston71_d7e5852a-974c-6d45-f81d-5a87bbd05d2a.json";
	
	private static final String OUT_DIR_NAME = "/src/main/resources/upload/glenn0-hermiston/";
	
	@Test
	public void shouldGetPatientResource() {
		log.info("Getting patient resource...");
		String json = FileUtil.getAsString(TEST_PATIENT_FILE);
		Bundle bundle = FhirJsonParser.parse(json, Bundle.class);
		log.info("Creating parser");
		BundleParser parser = new BundleParser(bundle);
		log.info("Getting patient");
		Patient patient = parser.getResourceForType(Patient.class);
		String patientJson = FhirJsonParser.serialize(patient);
		patientJson = JsonUtil.prettyPrint(patientJson);
		log.info(patientJson);
		log.info("Got patient, writing file...");
		File patientDir = FileUtil.getFromProjectRoot(OUT_DIR_NAME + "patient");
		FileUtil.rmdir(patientDir);
		File outFile = new File (patientDir, "Glenn0_Hermiston.json");
		FileUtil.write(patientJson, outFile);
		log.info("Done.");
	}
}
