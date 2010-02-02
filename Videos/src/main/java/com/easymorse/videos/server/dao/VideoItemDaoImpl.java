package com.easymorse.videos.server.dao;

import util.dao.HibernateDaoImpl;

import com.easymorse.videos.server.model.VideoItem;

public class VideoItemDaoImpl extends HibernateDaoImpl<VideoItem, String>
		implements VideoItemDao {
	{
		this.type=VideoItem.class;
	}
}
