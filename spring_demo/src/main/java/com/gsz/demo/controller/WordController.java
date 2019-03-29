package com.gsz.demo.controller;

import antlr.StringUtils;
import com.gsz.demo.bean.GSZUser;
import com.gsz.demo.bean.GSZWord;
import com.gsz.demo.model.CommonResponse;
import com.gsz.demo.model.VOWord;
import com.gsz.demo.repository.UserRepository;
import com.gsz.demo.repository.WordRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by markma on 19-3-29.
 */
@RestController
@RequestMapping("/api/v1/word")
@Slf4j
@Setter
@Getter
public class WordController {

	@Autowired
	WordRepository wordRepository;

	@Autowired
	UserRepository userRepository;



	@RequestMapping(method = RequestMethod.POST)
	public CommonResponse createWord(@RequestBody VOWord word) {
		CommonResponse response = new CommonResponse();
		Optional<GSZUser> user = userRepository.findById(word.getUserId());

		if (!user.isPresent()) {
			response.addException("user no found");
		}
		GSZWord existedWord = wordRepository.findByName(word.getName());
		if (existedWord != null) {
			response.addException("word with name:'" + word.getName() + "' already existed, not create");
		}

		if (response.isSuccess()) {
			GSZWord wordItem = new GSZWord(null, word.getName(), word.getDescription(), word.getSpelling(),
					user.get());
			try {
				wordItem = wordRepository.save(wordItem);
				response.addResultItem(String.valueOf(wordItem.getId()), wordItem);
			} catch (Exception e) {
				response.addException(e.getMessage());
				log.error(e.getMessage(), e);
			}
		}

		log.info("test");
		return response;
	}



	@RequestMapping(method = RequestMethod.GET)
	public CommonResponse getWord(@RequestParam(required = false) Long id, @RequestParam(required = false) String name,
			@RequestParam(required = false) Long userId) {

		CommonResponse response = new CommonResponse();
		if (id == null && name == null) {
			response.addException("either id nor name should be appear");
		}
		if (userId == null) {
			response.addException("userId is required");
		}

		if (response.isSuccess()) {
			Optional<GSZWord> wordItem = null;
			if (id != null) {
				wordItem = wordRepository.findById(id);
			} else {
				wordItem = Optional.ofNullable(wordRepository.findByName(name));

			}
			if (!wordItem.isPresent()) {
				response.addException("word not found");
			} else if (wordItem.get().getUser().getId() != userId) {
				response.addException("permission denied");
			}
			if (response.isSuccess()) {
				response.addResultItem(userId.toString(), wordItem.get());
			}
		}

		return response;
	}



	@RequestMapping(method = RequestMethod.PUT)
	public CommonResponse editWord(@RequestBody VOWord word) {
		CommonResponse response = new CommonResponse();

		if (word.getId() == null) {
			response.addException("ID required");
		}
		Optional<GSZWord> wordItemOpt = wordRepository.findById(word.getId());
		if (!wordItemOpt.isPresent()) {
			response.addException("word not found");
		}

		if (response.isSuccess()) {
			GSZWord wordItem = wordItemOpt.get();
			wordItem.setDescription(word.getDescription());
			wordItem.setSpelling(word.getSpelling());
			wordRepository.save(wordItem);
		}
		return response;
	}



	@RequestMapping(method = RequestMethod.DELETE)
	public CommonResponse deleteWord(@RequestParam(value = "ids") Long[] ids) {
		CommonResponse response = new CommonResponse();
		for (long id : ids) {
			try {
				wordRepository.deleteById(id);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				response.addException(e.getMessage() + " ID - " + id);
			}
		}
		return response;
	}
}
