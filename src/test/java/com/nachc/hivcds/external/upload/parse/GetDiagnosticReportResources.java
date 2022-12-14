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
		File patientDir = FileUtil.getFromProjectRoot(OUT_DIR_NAME + "diagnostic-reports");
		FileUtil.rmdir(patientDir);
		int cnt = 0;
		for(DiagnosticReport report : reports) {
			cnt++;
			BundleEntryComponent comp = new BundleEntryComponent();
			comp.setResource(report);
			reportsBundle.addEntry(comp);
			String reportJson = FhirJsonParser.serialize(report);
			reportJson = JsonUtil.prettyPrint(reportJson);
			String fileName = "GlennDiagReport-" + cnt + ".json";
			log.info("Got diagnostic report, writing file " + cnt + "...");
			File outFile = new File (patientDir, fileName);
			FileUtil.write(reportJson, outFile);
		}
		log.info("Done.");
	}
}
