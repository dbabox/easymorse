package com.easymorse.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoService {
	@RequestMapping("/a.json")
	public String doService(ModelMap modelMap) {
		modelMap.put("name", "张三");
		return "list";
	}
}
