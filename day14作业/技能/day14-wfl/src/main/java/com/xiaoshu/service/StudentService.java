package com.xiaoshu.service;

import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.dao.TeacherMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.StuVo;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentExample;
import com.xiaoshu.entity.StudentExample.Criteria;
import com.xiaoshu.entity.Teacher;


@Service
public class StudentService {

	@Autowired
	UserMapper userMapper;

	@Autowired
	StudentMapper sm;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private Destination queueTextDestination;

	// 新增
	public void addStudent(final Student t) throws Exception {
		
		sm.insert(t);
		jmsTemplate.send(queueTextDestination,new MessageCreator(){
			public Message createMessage(Session session) throws JMSException{
				
				String json = JSONObject.toJSONString(t);
				return session.createTextMessage(json);
			}
		});
	
	
	}

	// 修改
	public void updateStudent(Student t) throws Exception {
		sm.updateByPrimaryKeySelective(t);
	};

	// 删除
	public void deleteStudent(Integer id) throws Exception {
		sm.deleteByPrimaryKey(id);
	};



	// 通过用户名判断是否存在，（新增时不能重名）
	public Student existUserWithUserName(String userName) throws Exception {
		StudentExample example = new StudentExample();
		Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(userName);
		List<Student> userList = sm.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};

	

	public PageInfo<StuVo> findUserPage(StuVo s, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		List<StuVo> userList = sm.findPage(s);
		PageInfo<StuVo> pageInfo = new PageInfo<StuVo>(userList);
		return pageInfo;
	}
	
	@Autowired
	TeacherMapper tm;

	public List<Teacher> findTeacher() {
		// TODO Auto-generated method stub
		return tm.selectAll();
	}

	public List<StuVo> echarts() {
		// TODO Auto-generated method stub
		return sm.echarts();
	}

	public void addTeacher(Teacher t) {
		// TODO Auto-generated method stub
		tm.insert(t);
	}

	public List<StuVo> findStu(StuVo s) {
		// TODO Auto-generated method stub
		return sm.findPage(s);
	}

	public Integer findid(String tname) {
		// TODO Auto-generated method stub
		Teacher t = new Teacher();
		t.setName(tname);
		Teacher one = tm.selectOne(t);
		return one.getId();
	}


}
