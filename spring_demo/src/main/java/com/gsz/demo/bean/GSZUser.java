package com.gsz.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by markma on 19-2-22.
 */
@Entity
@Table(name = "gsz_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GSZUser {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long id;

	@Column(name = "login", unique = true, nullable = false)
	private String login;
	@Column(name = "email")
	private String email;
	@Column(name = "password", nullable = false)
	private String password;

	@OneToMany(cascade = CascadeType.ALL)
	private List<GSZWord> words;

}
