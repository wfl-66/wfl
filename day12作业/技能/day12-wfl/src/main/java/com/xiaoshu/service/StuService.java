package com.xiaoshu.service;

import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.MajorMapper;
import com.xiaoshu.dao.StuMapper;
import com.xiaoshu.entity.Major;
import com.xiaoshu.entity.Stu;
import com.xiaoshu.entity.StuExample;
import com.xiaoshu.entity.StuExample.Criteria;
import com.xiaoshu.entity.StuVo;


@Service
public class StuService {

	@Autowired
	MajorMapper mm;
	
	@Autowired
	StuMapper sm;

	

	// 新增
	public void addStu(Stu t) throws Exception {
		sm.insert(t);
	};

	// 修改
	public void updateStu(Stu t) throws Exception {
		sm.updateByPrimaryKeySelective(t);
	};

	// 删除
	public void deleteStu(Integer id) throws Exception {
		sm.deleteByPrimaryKey(id);
	};


	// 通过用户名判断是否存在，（新增时不能重名）
	public Stu existUserWithUserName(String userName) throws Exception {
		StuExample example = new StuExample();
		Criteria criteria = example.createCriteria();
		criteria.andSdnameEqualTo(userName);
		List<Stu> userList = sm.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};


	public PageInfo<StuVo> findUserPage(StuVo s, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		List<StuVo> userList = sm.findPage(s);
		PageInfo<StuVo> pageInfo = new PageInfo<StuVo>(userList);
		return pageInfo;
	}

	public List<Major> findMajor() {
		// TODO Auto-generated method stub
		return mm.selectAll();
	}

	public List<StuVo> findstu(StuVo s) {
		// TODO Auto-generated method stub
		return sm.findPage(s);
	}

	public List<StuVo> findEcharts() {
		// TODO Auto-generated method stub
		return sm.finEcharts();
	}
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private Destination queueTextDestination;
	
	public void addMajor(final Major m) {
		// TODO Auto-generated method stub
		mm.insert(m);
		jmsTemplate.send(queueTextDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				String json = JSONObject.toJSONString(m);
				return session.createTextMessage(json);
			}
		});
	}


}
