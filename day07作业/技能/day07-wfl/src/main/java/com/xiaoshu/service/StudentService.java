package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.dao.TeacherMapper;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentExample;
import com.xiaoshu.entity.StudentExample.Criteria;
import com.xiaoshu.entity.StudentVo;
import com.xiaoshu.entity.Teacher;


@Service
public class StudentService {

	@Autowired
	StudentMapper sm;

	@Autowired
	TeacherMapper tm;

	// 新增
	public void addStudent(Student t) throws Exception {
		sm.insert(t);
	};

	// 修改
	public void updateStudent(Student t) throws Exception {
		sm.updateByPrimaryKeySelective(t);
	};

	
	
	// 通过用户名判断是否存在，（新增时不能重名）
	public Student existUserWithUserName(String userName) throws Exception {
		StudentExample example = new StudentExample();
		Criteria criteria = example.createCriteria();
		criteria.andSnameEqualTo(userName);
		List<Student> userList = sm.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};

	

	public PageInfo<Student> findUserPage(Student s, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
	
		
		List<Student> userList = sm.findPage(s);
		PageInfo<Student> pageInfo = new PageInfo<Student>(userList);
		return pageInfo;
	}

	public List<Teacher> findTeacher() {
		// TODO Auto-generated method stub
		return tm.selectAll();
	}

	public List<StudentVo> findEcharts() {
		// TODO Auto-generated method stub
		return sm.findEcharts();
	}


}
