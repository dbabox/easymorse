package com.easymorse.videos.server;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VideoService {

	private static List<VideoItem> list;

	static {
		list = new ArrayList<VideoItem>();

		VideoItem videoItem = new VideoItem();
		videoItem.setId("1");
		videoItem.setContent("t1");
		list.add(videoItem);

		videoItem = new VideoItem();
		videoItem.setId("2");
		videoItem.setContent("t2");
		list.add(videoItem);

		videoItem = new VideoItem();
		videoItem.setId("3");
		videoItem.setContent("t3");
		list.add(videoItem);
	}

	@RequestMapping("/list.json")
	public Pagination<VideoItem, MyCondition> getResults(
			@ModelAttribute("pagination") Pagination<VideoItem, MyCondition> pagination,
			MyCondition condition) {
		// MyCondition condition = new MyCondition();
		// condition.setQuery("qq");
		// pagination.setCondition(condition);

		if (pagination == null) {
			pagination = new Pagination<VideoItem, MyCondition>();
		}

		if (condition != null) {
			pagination.setCondition(condition);
		}

		pagination.setSize(2);

		if (pagination.getNo() <= 1) {
			pagination.setNo(1);
			pagination.setResults(list.subList(0, 1));
		} else {
			pagination.setNo(2);
			pagination.setResults(list.subList(2, 2));
		}
		return pagination;
	}
}
