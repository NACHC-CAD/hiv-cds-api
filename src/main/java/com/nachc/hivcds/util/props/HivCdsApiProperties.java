package com.nachc.hivcds.util.props;

import java.io.File;
import java.util.Properties;

import com.nach.core.util.file.FileUtil;
import com.nach.core.util.props.PropertiesUtil;

public class HivCdsApiProperties {

	
	private static final String PROPS_FILE_NAME = "/auth/app.properties";
	
	private static final Properties PROPS;
	
	static {
		File file = FileUtil.getFile(PROPS_FILE_NAME);
		PROPS = PropertiesUtil.getAsProperties(file);
	}
	
	public static Properties getProperties() {
		return PROPS;
	}
	
	public static String get(String name) {
		return PROPS.getProperty(name);
	}
	
	public static String getPatientId() {
		return get("patientId");
	}
	
}
