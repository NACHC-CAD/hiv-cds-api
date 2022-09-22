package com.nachc.hivcds.api.hashivtest;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HasHivTestResponse {

	List<String> hivTestCodes = new ArrayList<String>();

	public boolean hasTest() {
		return this.hivTestCodes.size() > 0;
	}
	
}
