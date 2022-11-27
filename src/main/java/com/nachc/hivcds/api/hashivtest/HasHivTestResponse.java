package com.nachc.hivcds.api.hashivtest;

import java.util.ArrayList;
import java.util.List;

import org.nachc.tools.fhirtoomop.fhir.parser.r4.diagnosticreport.DiagnosticReportParser;

public class HasHivTestResponse {

	//
	// instance variables
	//
	
	private List<String> hivTestCodes = new ArrayList<String>();
	
	//
	// constructor
	//
	
	private List<DiagnosticReportParser> reports = new ArrayList<DiagnosticReportParser>();

	//
	// trivial getters and setters
	//
	
	public boolean hasTest() {
		return this.hivTestCodes.size() > 0;
	}

	public boolean isHasTest() {
		return this.hivTestCodes.size() > 0;
	}

	public boolean getHasTest() {
		return this.hivTestCodes.size() > 0;
	}

	public List<DiagnosticReportParser> getDiagnosticReports() {
		return this.reports;
	}
	
	public List<String> getHivTestCodes() {
		return hivTestCodes;
	}

	public void setHivTestCodes(List<String> hivTestCodes) {
		this.hivTestCodes = hivTestCodes;
	}
	
}
