package com.gsz.demo.repository;

import com.gsz.demo.bean.GSZUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Created by markma on 19-2-26.
 */

@Component
public interface UserRepository extends JpaRepository<GSZUser, Long> {

	public List<GSZUser> findByEmail(String email);
	public Optional<GSZUser> findByLogin(String login);
}
