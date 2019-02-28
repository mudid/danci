package com.gsz.demo.repository;

import com.gsz.demo.bean.GSZUser;
import com.gsz.demo.bean.GSZWord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by markma on 19-2-27.
 */

@Component
public interface WordRepository extends JpaRepository<GSZWord, Long>{
	public List<GSZWord> findByUser(GSZUser user,Pageable pageable);



}
