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
import com.xiaoshu.dao.BankMapper;
import com.xiaoshu.dao.PersonMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Bank;
import com.xiaoshu.entity.Person;
import com.xiaoshu.entity.PersonExample;
import com.xiaoshu.entity.PersonExample.Criteria;
import com.xiaoshu.entity.PersonVo;


@Service
public class PersonService {

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	PersonMapper pm;
	
	@Autowired
	BankMapper bm;

	
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private Destination queueTextDestination;

	// 新增
	public void addPerson(final Person t) throws Exception {
		
		pm.insert(t);
		jmsTemplate.send(queueTextDestination,new MessageCreator(){
			public Message createMessage(Session session) throws JMSException{
				
				String json = JSONObject.toJSONString(t);
				return session.createTextMessage(json);
			}
		});
	
	
	}

	// 修改
	public void updatePerson(Person t) throws Exception {
		pm.updateByPrimaryKeySelective(t);
	};



	

	// 通过用户名判断是否存在，（新增时不能重名）
	public Person existUserWithUserName(String userName) throws Exception {
		PersonExample example = new PersonExample();
		Criteria criteria = example.createCriteria();
		criteria.andPNameEqualTo(userName);
		List<Person> userList = pm.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};

	

	public PageInfo<PersonVo> findUserPage(PersonVo p, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		List<PersonVo> userList = pm.findPage(p);
		PageInfo<PersonVo> pageInfo = new PageInfo<PersonVo>(userList);
		return pageInfo;
	}
	
	
	

	public List<Bank> findBank() {
		// TODO Auto-generated method stub
		return bm.selectAll();
	}

	public List<PersonVo> echartsPerson() {
		// TODO Auto-generated method stub
		return pm.echartsPerson();
	}

	public int findbName(String bName) {
		Bank b = new Bank();
		b.setbName(bName);
		Bank one = bm.selectOne(b);
		return one.getbId();
	}


}
