package com.gsz.demo;

import com.gsz.demo.security.GszUserDetailService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by markma on 19-2-25.
 */

@Getter
@Setter
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private GszUserDetailService gszUserDetailService;



	@Override
	protected void configure(HttpSecurity http) throws Exception {

		super.configure(http);
	}



	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(getAuthenticationProvider());
	}


	public AuthenticationProvider getAuthenticationProvider(){
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(getGszUserDetailService());
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		return provider;
	}
}
