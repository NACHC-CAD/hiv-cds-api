package com.nachc.hivcds.util.valueset.hivtest;

import java.util.List;

import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.ValueSet;
import org.nachc.tools.fhirtoomop.fhir.parser.r4.valueset.ValueSetParser;

import com.nach.core.util.fhir.parser.FhirJsonParser;
import com.nach.core.util.file.FileUtil;

public class AddCodesR4 {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HivTestCodes.class);	
	
	protected static void exec(String fileName, List<String> rtn) {
		log.info("--- FILE -------------------------");
		log.info(fileName);
		String json = FileUtil.getAsString(fileName);
		ValueSet valueSet = FhirJsonParser.parse(json, ValueSet.class);
		ValueSetParser parser = new ValueSetParser(valueSet);
		List<Coding> codingList = parser.getConcepts();
		log.info("Got " + codingList.size() + " codings");
		for(Coding coding : codingList) {
			rtn.add(coding.getCode());
		}
	}
	
}
