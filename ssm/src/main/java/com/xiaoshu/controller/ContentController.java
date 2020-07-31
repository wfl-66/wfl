package com.xiaoshu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Content;
import com.xiaoshu.entity.ContentCategory;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.service.ContentCategoryService;
import com.xiaoshu.service.ContentService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;

@Controller
@RequestMapping("content")
public class ContentController {
	static Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private ContentService cs;
	
	@Autowired
	private ContentCategoryService ccs ;
	
	@Autowired
	private OperationService operationService;
	
	@RequestMapping("contentIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<ContentCategory> ContentCategory = ccs.findcss();
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		request.setAttribute("ContentCategory", ContentCategory);
		return "content";
	}
	
	@RequestMapping(value="contentList",method=RequestMethod.POST)
	public void contentList(HttpServletRequest request,HttpServletResponse response,String offset,String limit,Content content) throws Exception{
		try {
			
			
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<Content> contentList= cs.show(content,pageNum,pageSize);
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",contentList.getTotal() );
			jsonObj.put("rows", contentList.getList());
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("广告信息展示错误",e);
			throw e;
		}
	}
	
	
}
