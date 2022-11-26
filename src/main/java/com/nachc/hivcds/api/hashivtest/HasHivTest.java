package com.nachc.hivcds.api.hashivtest;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.DiagnosticReport;
import org.nachc.tools.fhirtoomop.fhir.parser.bundle.IBundleParser;
import org.nachc.tools.fhirtoomop.fhir.parser.r4.bundle.BundleParser;
import org.nachc.tools.fhirtoomop.fhir.parser.r4.diagnosticreport.DiagnosticReportParser;

import com.nachc.hivcds.util.valueset.hivtest.HivTestCodes;

public class HasHivTest {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HasHivTest.class);	
	
	public static HasHivTestResponse exec(List<String> patientJsonList) {
		HasHivTestResponse rtn = new HasHivTestResponse();
		return exec(patientJsonList, rtn);
	}

	public static HasHivTestResponse exec(List<String> patientJsonList, HasHivTestResponse rtn) {
		for(String json : patientJsonList) {
			exec(json, rtn);
		}
		return rtn;
	}

	public static HasHivTestResponse exec(String patientJson) {
		HasHivTestResponse rtn = new HasHivTestResponse();
		return exec(patientJson, rtn);
	}

	public static HasHivTestResponse exec(String patientJson, HasHivTestResponse rtn) {
		// get the list of diagnostic reports
		IBundleParser parser = null;
		try {
			parser = new BundleParser(patientJson);
		} catch(Exception exp) {
			parser = new org.nachc.tools.fhirtoomop.fhir.parser.bundle.BundleParser(patientJson);
		}
		List<DiagnosticReport> diagReportList = parser.getResourceListForType(DiagnosticReport.class);
		log.info("got " + diagReportList.size() + " diagnostic reports");
		// get the list of test codes
		List<String> hivTestCodes = HivTestCodes.get();
		log.info("got " + hivTestCodes.size() + " hivTestCodes");
		// get a list of all of the diagnostic report codes
		List<String> diagCodesForPatient = new ArrayList<String>();
		for(DiagnosticReport diagReport : diagReportList) {
			DiagnosticReportParser diagReportParser = new DiagnosticReportParser(diagReport);
			rtn.getDiagnosticReports().add(diagReportParser);
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
