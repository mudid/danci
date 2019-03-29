package com.gsz.demo.controller;

import com.gsz.demo.bean.GSZUser;
import com.gsz.demo.bean.GSZUserDetails;
import com.gsz.demo.bean.GSZWord;
import com.gsz.demo.model.VOWordSource;
import com.gsz.demo.repository.UserRepository;
import com.gsz.demo.repository.WordRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by markma on 19-2-25.
 */
@Controller
@Slf4j
@Setter
@Getter
public class IndexController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WordRepository wordRepository;

	private int pageSize = 5;

	private String           uploadTempFolder = "/home/markma/codebase/spring_youdao/danci/spring_demo/temp/";
	private SimpleDateFormat sdf              = new SimpleDateFormat("yyyy-MM-dd");



	@RequestMapping(path = "/")
	public String index(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof GSZUserDetails) {
			model.addAttribute("user", ((GSZUserDetails) principal).getUser());
		}
		log.info("test index");
		return "index";
	}



	@RequestMapping(path = "/test/index")
	public String testIndex(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof GSZUserDetails) {
			GSZUser user = getUserRepository().getOne(((GSZUserDetails) principal).getUser().getId());
			model.addAttribute("user", user);
			model.addAttribute("pageSize", ((user.getWords().size() - 1) / getPageSize() + 1));

		}

		log.info("test index");
		return "test/index";
	}



	@RequestMapping(path = "/test/prac")
	public String practiceIndex(Model model, @RequestParam(name = "index") Integer index) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof GSZUserDetails) {
			GSZUser user = getUserRepository().getOne(((GSZUserDetails) principal).getUser().getId());
			model.addAttribute("user", user);
			model.addAttribute("pageIndex", index);
			model.addAttribute("totalPage", ((user.getWords().size() - 1) / getPageSize() + 1));
			Sort sort = new Sort(Sort.Direction.ASC, "id");
			Pageable pagination = PageRequest.of(index-1, getPageSize(), sort);
			List<GSZWord> words = getWordRepository().findByUser(user, pagination);
			model.addAttribute("words", words);

		}

		log.info("test index");
		return "test/prac";
	}



	@RequestMapping(path = "/create")
	public String create(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof GSZUserDetails) {
			GSZUser user = getUserRepository().getOne(((GSZUserDetails) principal).getUser().getId());
			model.addAttribute("user", user);
			List words = user.getWords();
			if (words == null) {
				words = new ArrayList();
			}
			for (int i = 2; i <= 500; i++) {
				words.add(new GSZWord(null, "wd" + i, "test" + i, "sad" + i, null));
			}
			user.setWords(words);
			getUserRepository().save(user);
		}
		log.info("create index");
		return "index";
	}



	@RequestMapping(path = "/upload", method = { RequestMethod.GET })
	public String uploadIndex(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof GSZUserDetails) {
			GSZUser user = getUserRepository().getOne(((GSZUserDetails) principal).getUser().getId());
			model.addAttribute("user", user);
		}
		log.info("upload index");
		return "upload";
	}



	@RequestMapping(path = "/upload", method = { RequestMethod.POST })
	public String upload(Model model, @RequestParam(name = "dicFile", required = true) MultipartFile file)
			throws IOException {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof GSZUserDetails) {
			GSZUser user = getUserRepository().getOne(((GSZUserDetails) principal).getUser().getId());
			model.addAttribute("user", user);
			if (file != null) {
				File sourceFile = new File(getUploadTempFolder() + file.getOriginalFilename());
				file.transferTo(new File(getUploadTempFolder() + file.getOriginalFilename()));
				createWords(sourceFile,user);
			}
		}

		log.info("upload dictionary");
		return "index";
	}



	@RequestMapping(path = "/marktest")
	public String testBase() throws JAXBException {
		VOWordSource source = new VOWordSource("test1", "test2", "test1");

		JAXBContext context = JAXBContext.newInstance(VOWordSource.class);


		Marshaller marshaller = context.createMarshaller();
		Unmarshaller unmarshaller = context.createUnmarshaller();
		marshaller.marshal(source, System.out);

		VOWordSource source1 = (VOWordSource) unmarshaller
				.unmarshal(new File("/home/markma/codebase/spring_youdao/danci/spring_demo/temp/word.xml"));

		log.info(source1.toString());
		return "index";
	}



	public void createWords(File file, GSZUser user) {
		try {
			JAXBContext context = JAXBContext.newInstance(VOWordSource.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			VOWordSource source1 = (VOWordSource) unmarshaller
					.unmarshal(new File("/home/markma/codebase/spring_youdao/danci/spring_demo/temp/word.xml"));

			if(source1!=null){
				user.getWords().add(new GSZWord(null,source1.getWord(),source1.getTrans(),source1.getPhonetic(),null));
				getUserRepository().save(user);
			}

		} catch (Exception e) {
			log.error("文件内容不对", e);
		}
	}
}
