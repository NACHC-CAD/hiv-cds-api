package com.nachc.hivcds.api.hashivtest;

import java.util.ArrayList;
import java.util.List;

public class HasHivTestResponse {

	private List<String> hivTestCodes = new ArrayList<String>();

	public boolean hasTest() {
		return this.hivTestCodes.size() > 0;
	}

	public List<String> getHivTestCodes() {
		return hivTestCodes;
	}

	public void setHivTestCodes(List<String> hivTestCodes) {
		this.hivTestCodes = hivTestCodes;
	}
	
}
