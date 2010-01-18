package com.easymorse.web;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoService {
	@RequestMapping("/a.json")
	public String doService(ModelMap modelMap) {
		Pagination pagination = new Pagination();
		pagination.setPageNo(2);
		pagination.setResults(new ArrayList<Object>());
		Book book = new Book();
		book.setId("1");
		book.setName("五百年来谁著史");
		book.setAuthors(new ArrayList<Author>());
		pagination.getResults().add(book);
		Author author = new Author();
		author.setId("1");
		author.setName("张三");
		book.getAuthors().add(author);
		author = new Author();
		author.setId("2");
		author.setName("李四");
		book.getAuthors().add(author);

		modelMap.put("page", pagination);
		return "list";
	}
}
