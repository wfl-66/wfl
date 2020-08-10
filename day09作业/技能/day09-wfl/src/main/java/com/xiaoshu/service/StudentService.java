package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.CourseMapper;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.entity.Course;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentExample;
import com.xiaoshu.entity.StudentExample.Criteria;
import com.xiaoshu.entity.StudentVo;

@Service
public class StudentService {

	@Autowired
	StudentMapper sm;

	@Autowired
	CourseMapper cm;

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
		criteria.andNameEqualTo(userName);
		List<Student> userList = sm.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};
	
	
	public PageInfo<StudentVo> findUserPage(StudentVo sv, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		List<StudentVo> userList = sm.findPage(sv);
		PageInfo<StudentVo> pageInfo = new PageInfo<StudentVo>(userList);
		return pageInfo;
	}

	public List<Course> findCourse() {
		// TODO Auto-generated method stub
		return cm.selectAll();
	}

	public List<StudentVo> export(StudentVo sv) {
		// TODO Auto-generated method stub
		return sm.findPage(sv);
	}

	public List<StudentVo> findEcharts() {
		// TODO Auto-generated method stub
		return sm.findEcharts();
	}


}
