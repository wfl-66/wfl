package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.xiaoshu.dao.DeviceMapper;
import com.xiaoshu.dao.DevicetypeMapper;
import com.xiaoshu.entity.Device;
import com.xiaoshu.entity.DeviceExample;
import com.xiaoshu.entity.DeviceExample.Criteria;
import com.xiaoshu.entity.DeviceVo;
import com.xiaoshu.entity.Devicetype;

@Service
public class DeviceService {
/*
	@Autowired
	UserMapper userMapper;

	// 查询所有
	public List<User> findUser(User t) throws Exception {
		return userMapper.select(t);
	};

	// 数量
	public int countUser(User t) throws Exception {
		return userMapper.selectCount(t);
	};

	// 通过ID查询
	public User findOneUser(Integer id) throws Exception {
		return userMapper.selectByPrimaryKey(id);
	};

	// 新增
	public void addUser(User t) throws Exception {
		userMapper.insert(t);
	};

	// 修改
	public void updateUser(User t) throws Exception {
		userMapper.updateByPrimaryKeySelective(t);
	};

	// 删除
	public void deleteUser(Integer id) throws Exception {
		userMapper.deleteByPrimaryKey(id);
	};

	// 登录
	public User loginUser(User user) throws Exception {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andPasswordEqualTo(user.getPassword()).andUsernameEqualTo(user.getUsername());
		List<User> userList = userMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};

	// 通过用户名判断是否存在，（新增时不能重名）
	public User existUserWithUserName(String userName) throws Exception {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(userName);
		List<User> userList = userMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};

	// 通过角色判断是否存在
	public User existUserWithRoleId(Integer roleId) throws Exception {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andRoleidEqualTo(roleId);
		List<User> userList = userMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	}
*/
	@Autowired
	private DeviceMapper deviceMapper;
	@Autowired
	private DevicetypeMapper devicetypeMapper;
	public PageInfo<DeviceVo> findUserPage(DeviceVo deviceVo, int pageNum, int pageSize, String ordername, String order) {
		PageHelper.startPage(pageNum, pageSize);
		
		List<DeviceVo> userList = deviceMapper.findPage(deviceVo);
		
		PageInfo<DeviceVo> pageInfo = new PageInfo<DeviceVo>(userList);
		return pageInfo;
	}
	public List<Devicetype> findDevicetype() {
		// TODO Auto-generated method stub
		return devicetypeMapper.selectAll();
	}
	// 新增
		public void addUser(Device t) throws Exception {
			deviceMapper.insert(t);
		};

		// 修改
		public void updateUser(Device t) throws Exception {
			deviceMapper.updateByPrimaryKeySelective(t);
		};

		// 删除
		public void deleteUser(Integer id) throws Exception {
			deviceMapper.deleteByPrimaryKey(id);
		};

	

		// 通过用户名判断是否存在，（新增时不能重名）
		public Device existUserWithUserName(String userName) throws Exception {
			DeviceExample example = new DeviceExample();
			Criteria criteria = example.createCriteria();
			criteria.andDevicenameEqualTo(userName);
			List<Device> userList = deviceMapper.selectByExample(example);
			return userList.isEmpty()?null:userList.get(0);
		}
		public List<DeviceVo> findPage(DeviceVo device) {
			// TODO Auto-generated method stub
			return deviceMapper.findPage(device);
		};


}
