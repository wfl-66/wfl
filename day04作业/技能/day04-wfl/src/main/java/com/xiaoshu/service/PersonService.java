package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.CompanyMapper;
import com.xiaoshu.dao.PersonMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Company;
import com.xiaoshu.entity.Person;
import com.xiaoshu.entity.PersonExample;
import com.xiaoshu.entity.PersonExample.Criteria;
import com.xiaoshu.entity.PersonVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;

@Service
public class PersonService {

	@Autowired
	UserMapper userMapper;
	
	@Autowired 
	PersonMapper pm;
	
	@Autowired
	CompanyMapper cm;

	// 新增
	public void addPerson(Person t) throws Exception {
		pm.insert(t);
	};

	// 修改
	public void updatePerson(Person t) throws Exception {
		pm.updateByPrimaryKeySelective(t);
	};

	// 删除
	public void deletePerson(Integer id) throws Exception {
		pm.deleteByPrimaryKey(id);
	};



	// 通过用户名判断是否存在，（新增时不能重名）
	public Person existUserWithUserName(String pName) throws Exception {
		PersonExample example = new PersonExample();
		Criteria criteria = example.createCriteria();
		criteria.andPNameEqualTo(pName);
		List<Person> userList = pm.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};

	public PageInfo<PersonVo> findUserPage(PersonVo personVo, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<PersonVo> userList = pm.findPage(personVo);
		return new PageInfo<>(userList);
	}

	public List<Company> findCompany() {
		// TODO Auto-generated method stub
		return cm.selectAll();
	}


}
