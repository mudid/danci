package com.gsz.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by markma on 19-3-29.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Error {
	private List<String> errorMessage = new ArrayList<String>();
}
