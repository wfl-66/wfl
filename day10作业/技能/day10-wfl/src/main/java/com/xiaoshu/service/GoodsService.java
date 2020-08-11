package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.GoodsMapper;
import com.xiaoshu.dao.GoodstypeMapper;
import com.xiaoshu.entity.Goods;
import com.xiaoshu.entity.GoodsExample;
import com.xiaoshu.entity.GoodsExample.Criteria;
import com.xiaoshu.entity.GoodsVo;
import com.xiaoshu.entity.Goodstype;

@Service
public class GoodsService {

	@Autowired
	GoodsMapper gm;
	
	@Autowired
	GoodstypeMapper gtm;

	// 新增
	public void addGoods(Goods t) throws Exception {
		gm.insert(t);
	};

	// 修改
	public void updateGoods(Goods t) throws Exception {
		gm.updateByPrimaryKeySelective(t);
	};

	// 通过用户名判断是否存在，（新增时不能重名）
	public Goods existUserWithUserName(String userName) throws Exception {
		GoodsExample example = new GoodsExample();
		Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(userName);
		List<Goods> userList = gm.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};

	

	public PageInfo<GoodsVo> findUserPage(GoodsVo gv, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<GoodsVo> userList = gm.findPage(gv);
		PageInfo<GoodsVo> pageInfo = new PageInfo<GoodsVo>(userList);
		return pageInfo;
	}

	public List<Goodstype> findType() {
		// TODO Auto-generated method stub
		return gtm.selectAll();
	}

	@Autowired private RedisTemplate redisTemplate;
	public void addGoodstype(Goodstype g) {
		// TODO Auto-generated method stub
		gtm.insert(g);
		redisTemplate.boundHashOps("glist").put("gs", g.toString());
	}


}
