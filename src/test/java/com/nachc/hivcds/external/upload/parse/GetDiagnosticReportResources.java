package com.nachc.hivcds.external.upload.parse;

import java.io.File;
import java.util.List;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.Bundle.BundleType;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.hl7.fhir.r4.model.Patient;
import org.junit.Test;
import org.nachc.tools.fhirtoomop.fhir.parser.r4.bundle.BundleParser;

import com.nach.core.util.fhir.parser.FhirJsonParser;
import com.nach.core.util.file.FileUtil;
import com.nach.core.util.json.JsonUtil;
import com.nachc.hivcds.api.hashivtest.HasHivTestIntegrationTest;

public class GetDiagnosticReportResources {

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
		List<DiagnosticReport> reports = parser.getResourceListForType(DiagnosticReport.class);
		Bundle reportsBundle = new Bundle();
		reportsBundle.setType(BundleType.COLLECTION);
		for(DiagnosticReport report : reports) {
			BundleEntryComponent comp = new BundleEntryComponent();
			comp.setResource(report);
			reportsBundle.addEntry(comp);
		}
		String reportsJson = FhirJsonParser.serialize(reportsBundle);
		reportsJson = JsonUtil.prettyPrint(reportsJson);
		log.info(reportsJson);
		log.info("Got patient, writing file...");
		File patientDir = FileUtil.getFromProjectRoot(OUT_DIR_NAME + "diagnosticreports");
		FileUtil.rmdir(patientDir);
		File outFile = new File (patientDir, "Glenn0_Hermiston_DiagnosticReports.json");
		FileUtil.write(reportsJson, outFile);
		log.info("Done.");
	}
}
