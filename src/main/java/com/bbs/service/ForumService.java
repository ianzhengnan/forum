package com.bbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbs.dao.BoardDao;
import com.bbs.dao.PostDao;
import com.bbs.dao.TopicDao;
import com.bbs.dao.UserDao;

@Service
public class ForumService {
	
	@Autowired
	private TopicDao topicDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private BoardDao boardDao;
	@Autowired
	private PostDao postDao;
	
	/**
	 * 发表一个主题帖子,用户积分加10，论坛版块的主题帖数加1
	 * @param topic
	 */
	
}
