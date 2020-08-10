package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoshu.dao.CourseMapper;
import com.xiaoshu.entity.Course;
import com.xiaoshu.entity.CourseExample;
import com.xiaoshu.entity.CourseExample.Criteria;

@Service
public class CourseService {
	
	@Autowired
	private CourseMapper cm;

	public Course existCourseWithUserName(String userName) throws Exception {
		CourseExample example = new CourseExample();
		Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(userName);
		List<Course> userList = cm.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	}

	public void addCourse(Course c) {
		// TODO Auto-generated method stub
		cm.insert(c);
	};

	

}
