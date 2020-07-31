package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.ContentMapper;
import com.xiaoshu.entity.Content;

@Service
public class ContentService {
	@Autowired
	ContentMapper cm;

	public PageInfo<Content> show(Content content, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Content> contentList = cm.show(content);
		PageInfo<Content> pageInfo = new PageInfo<Content>(contentList);
		return pageInfo;
	}
}
