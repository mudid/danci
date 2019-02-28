package com.gsz.demo.bean;

import lombok.*;

import javax.xml.bind.annotation.*;

/**
 * Created by markma on 19-2-28.
 */

@XmlRootElement(name = "item")
@XmlAccessorType(value = XmlAccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GSZWordSource {
	private String word;
	private String trans;
	private String phonetic;


}
