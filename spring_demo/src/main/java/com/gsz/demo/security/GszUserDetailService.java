package com.gsz.demo.security;

import com.gsz.demo.bean.GSZUser;
import com.gsz.demo.bean.GSZUserDetails;
import com.gsz.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by markma on 19-2-26.
 */

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GszUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;



	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		Optional<GSZUser> user = getUserRepository().findByLogin(s);
		return user.map(userDetail ->
				new GSZUserDetails(userDetail.getLogin(),
						userDetail.getPassword(), userDetail,
						AuthorityUtils.NO_AUTHORITIES)).orElseThrow(() -> new UsernameNotFoundException(
				"user not found by name =" + s));
	}
}
