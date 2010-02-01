package com.easymorse.videos.server;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VideoService {

	@RequestMapping("/isLogined.json")
	public void isLogined() {
	}

	@RequestMapping("/browse.json")
	public void browse(ModelMap modelMap) {
		modelMap.put("result", "browse");
	}
}
