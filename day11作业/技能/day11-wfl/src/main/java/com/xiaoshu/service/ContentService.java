package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.ContentMapper;
import com.xiaoshu.dao.ContentcategoryMapper;
import com.xiaoshu.entity.Content;
import com.xiaoshu.entity.ContentExample;
import com.xiaoshu.entity.ContentExample.Criteria;
import com.xiaoshu.entity.ContentVo;
import com.xiaoshu.entity.Contentcategory;


@Service
public class ContentService {

	@Autowired
	private ContentMapper cm;
	
	@Autowired
	private ContentcategoryMapper ccm;
	
	// 新增
		public void addContent(Content t) throws Exception {
			cm.insert(t);
		};
	// 修改
	public void updateContent(Content t) throws Exception {
		cm.updateByPrimaryKeySelective(t);
	};

	// 删除
	public void deleteContent(Integer id) throws Exception {
		cm.deleteByPrimaryKey(id);
	};

	// 通过用户名判断是否存在，（新增时不能重名）
		public Content existUserWithUserName(String userName) throws Exception {
			ContentExample example = new ContentExample();
			Criteria criteria = example.createCriteria();
			criteria.andContenttitleEqualTo(userName);
			List<Content> userList = cm.selectByExample(example);
			return userList.isEmpty()?null:userList.get(0);
		};



	

	public PageInfo<ContentVo> findUserPage(ContentVo cv, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ContentVo> userList = cm.findPage(cv);
		PageInfo<ContentVo> pageInfo = new PageInfo<ContentVo>(userList);
		return pageInfo;
	}

	public List<Contentcategory> findcategory() {
		// TODO Auto-generated method stub
		return ccm.selectAll();
	}
	public List<ContentVo> findecharts() {
		// TODO Auto-generated method stub
		return cm.findecharts();
	}


}
