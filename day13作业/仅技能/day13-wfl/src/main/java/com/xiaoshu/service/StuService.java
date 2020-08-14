package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	StuMapper sm;
	
	@Autowired
	MajorMapper mm;
	
	
	
	
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
		criteria.andSNameEqualTo(userName);
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
		return mm.selectAll();
	}

	public List<StuVo> findexport(StuVo s) {
		return sm.findPage(s);
	}

	public List<StuVo> echartsStu() {
		return sm.findEcharts();
	}


}
