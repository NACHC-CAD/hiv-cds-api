package com.nachc.hivcds.api.hashivtest;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.DiagnosticReport;
import org.nachc.tools.fhirtoomop.fhir.parser.r4.bundle.BundleParser;
import org.nachc.tools.fhirtoomop.fhir.parser.r4.diagnosticreport.DiagnosticReportParser;

import com.nachc.hivcds.util.valueset.hivtest.HivTestCodes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HasHivTest {
	
	public static HasHivTestResponse exec(String patientJson) {
		// get the list of diagnostic reports
		HasHivTestResponse rtn = new HasHivTestResponse();
		BundleParser parser = new BundleParser(patientJson);
		List<DiagnosticReport> diagReportList = parser.getResourceListForType(DiagnosticReport.class);
		log.info("got " + diagReportList.size() + " diagnostic reports");
		// get the list of test codes
		List<String> hivTestCodes = HivTestCodes.get();
		log.info("got " + hivTestCodes.size() + " hivTestCodes");
		// get a list of all of the diagnostic report codes
		List<String> diagCodesForPatient = new ArrayList<String>();
		for(DiagnosticReport diagReport : diagReportList) {
			DiagnosticReportParser diagReportParser = new DiagnosticReportParser(diagReport);
			List<String> codesInReport = diagReportParser.getCodes();
			diagCodesForPatient.addAll(codesInReport);
		}
		log.info("Got " + diagCodesForPatient.size() + " diag report codes for patient.");
		for(String diagCode : diagCodesForPatient) {
			if(hivTestCodes.contains(diagCode)) {
				rtn.getHivTestCodes().add(diagCode);
			}
		}
		return rtn;
	}

}
