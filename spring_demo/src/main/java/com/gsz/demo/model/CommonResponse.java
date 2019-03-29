package com.gsz.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by markma on 19-3-29.
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommonResponse {
	private boolean success = true;
	private int   responseCode = 200;
	private Map<String, Object> result;
	private Error error        = new Error();



	public void addException(String errorMessage) {
		addException(errorMessage, true, 500);
	}



	public void addResultItem(String key, Object val) {
		if (result == null) {
			result = new HashMap<String, Object>();
		}
		result.put(key, val);
	}



	public void addException(String errorMessage, int responseCode) {
		addException(errorMessage, true, responseCode);
	}



	public void addException(String errorMessage, boolean clearResult, int responseCode) {
		if (clearResult) {
			result = null;
		}
		setResponseCode(responseCode);
		setSuccess(false);
		getError().getErrorMessage().add(errorMessage);
	}

}
