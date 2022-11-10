package com.nachc.hivcds.util.valueset.hivtest;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.ValueSet;
import org.nachc.tools.fhirtoomop.fhir.parser.r4.valueset.ValueSetParser;

import com.nach.core.util.fhir.parser.FhirJsonParser;
import com.nach.core.util.file.FileUtil;

public class HivTestCodes {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HivTestCodes.class);	
	
	private static final String[] FILES = {
			"/hiv-cds/vocabulary/valueset/generated/valueset-nachc-a2-de2.json",
			"/hiv-cds/vocabulary/valueset/generated/valueset-nachc-a2-de1-codes-grouper.json",
			"/hiv-cds/vocabulary/valueset/generated/valueset-nachc-a2-de217.json",
			"/hiv-cds/vocabulary/valueset/generated/valueset-nachc-a2-de173.json"
	};
	
	public static List<String> get() {
		ArrayList<String> rtn = new ArrayList<String>();
		for(String filePath : FILES) {
			addCodes(filePath, rtn);
		}
		return rtn;
	}
	
	private static void addCodes(String fileName, List<String> rtn) {
		try {
			AddCodesR4.exec(fileName, rtn);
		} catch(Exception exp) {
			AddCodesDstu3.exec(fileName, rtn);
		}
	}
	
}
