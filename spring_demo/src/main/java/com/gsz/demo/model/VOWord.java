package com.gsz.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by markma on 19-3-29.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VOWord {

	private Long id;
	private String name;
	private String description;
	private String spelling;
	private Long userId;

}
