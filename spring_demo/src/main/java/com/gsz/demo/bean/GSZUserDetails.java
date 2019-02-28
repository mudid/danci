package com.gsz.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by markma on 19-2-27.
 */

@Getter
@Setter
public class GSZUserDetails extends User {

	private GSZUser user;


	public GSZUserDetails(String username, String password, GSZUser user,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		setUser(user);
	}

}
