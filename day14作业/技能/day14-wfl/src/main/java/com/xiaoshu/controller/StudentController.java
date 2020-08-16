package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.StuVo;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.Teacher;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.StudentService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

@Controller
@RequestMapping("student")
public class StudentController extends LogController{
	static Logger logger = Logger.getLogger(StudentController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	private StudentService ss;
	
	
	@RequestMapping("studentIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Role> roleList = roleService.findRole(new Role());
		List<Teacher> tList = ss.findTeacher();
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		request.setAttribute("roleList", roleList);
		request.setAttribute("tList", tList);
		return "student";
	}
	
	
	@RequestMapping(value="studentList",method=RequestMethod.POST)
	public void userList(HttpServletRequest request,StuVo s,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			
			
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<StuVo> userList= ss.findUserPage(s,pageNum,pageSize);
			
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",userList.getTotal() );
			jsonObj.put("rows", userList.getList());
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误",e);
			throw e;
		}
	}
	
	
	// 新增或修改
	@RequestMapping("reserveStudent")
	public void reserveUser(HttpServletRequest request,Student s,HttpServletResponse response){
		Integer userId = s.getId();
		JSONObject result=new JSONObject();
		try {
			if (userId != null) {   // userId不为空 说明是修改
				
					ss.updateStudent(s);
					result.put("success", true);
				
				
			}else {   // 添加
				if(ss.existUserWithUserName(s.getName())==null){  // 没有重复可以添加
					ss.addStudent(s);
					result.put("success", true);
				} else {
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	// 新增或修改
		@RequestMapping("reserveTeacher")
		public void reserveTeacher(HttpServletRequest request,Teacher t,HttpServletResponse response){
			JSONObject result=new JSONObject();
			try {
				
						ss.addTeacher(t);
						result.put("success", true);
					
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("保存用户信息错误",e);
				result.put("success", true);
				result.put("errorMsg", "对不起，操作失败");
			}
			WriterUtil.write(response, result.toString());
		}
	
	
	
	@RequestMapping("deleteStudent")
	public void delUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				ss.deleteStudent(Integer.parseInt(id));
			}
			result.put("success", true);
			result.put("delNums", ids.length);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	@RequestMapping("echartsStudent")
	public void echartsStudent(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			
				List<StuVo> list =  ss.echarts();
			
			result.put("success", true);
			result.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	
	/**
	 * 备份
	 */
	@RequestMapping("exportStudent")
	public void backup(HttpServletRequest request,HttpServletResponse response){
		JSONObject result = new JSONObject();
		try {
			String time = TimeUtil.formatTime(new Date(), "yyyyMMddHHmmss");
		    String excelName = "手动备份"+time;
			StuVo s = new StuVo();
			List<StuVo> list = ss.findStu(s);
			String[] handers = {"学生编号","学生姓名","老师姓名","年龄","所属年级","入校时间"};
			// 1导入硬盘
			ExportExcelToDisk(request,handers,list, excelName);
			
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("", "对不起，备份失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	
	// 导出到硬盘
	@SuppressWarnings("resource")
	private void ExportExcelToDisk(HttpServletRequest request,
			String[] handers, List<StuVo> list, String excleName) throws Exception {
		
		try {
			HSSFWorkbook wb = new HSSFWorkbook();//创建工作簿
			HSSFSheet sheet = wb.createSheet("操作记录备份");//第一个sheet
			HSSFRow rowFirst = sheet.createRow(0);//第一个sheet第一行为标题
			rowFirst.setHeight((short) 500);
			for (int i = 0; i < handers.length; i++) {
				sheet.setColumnWidth((short) i, (short) 4000);// 设置列宽
			}
			//写标题了
			for (int i = 0; i < handers.length; i++) {
			    //获取第一行的每一个单元格
			    HSSFCell cell = rowFirst.createCell(i);
			    //往单元格里面写入值
			    cell.setCellValue(handers[i]);
			}
			for (int i = 0;i < list.size(); i++) {
			    //获取list里面存在是数据集对象
			    StuVo s = list.get(i);
			    //创建数据行
			    HSSFRow row = sheet.createRow(i+1);
			    //设置对应单元格的值
			    row.setHeight((short)400);   // 设置每行的高度
			    //"序号","操作人","IP地址","操作时间","操作模块","操作类型","详情"
			   /* if(s.getAge().equals(1) && s.getGrade().equals("一年级")){*/
			    	row.createCell(0).setCellValue(s.getCode());
				    row.createCell(1).setCellValue(s.getName());
				    row.createCell(2).setCellValue(s.getTname());
				    row.createCell(3).setCellValue(s.getAge());
				    row.createCell(4).setCellValue(s.getGrade());
				    row.createCell(5).setCellValue(TimeUtil.formatTime(s.getEntrytime(), "yyyy/MM/dd"));
			    //}
			}
			//写出文件（path为文件路径含文件名）
				OutputStream os;
				File file = new File("d:/pic/"+excleName+".xls");
				
				if (!file.exists()){//若此目录不存在，则创建之  
					file.createNewFile();  
					logger.debug("创建文件夹路径为："+ file.getPath());  
	            } 
				os = new FileOutputStream(file);
				wb.write(os);
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
	}
	
	
	@RequestMapping("importStudent")
	public void importStudent(MultipartFile excelFile ,HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			HSSFWorkbook wb = new HSSFWorkbook(excelFile.getInputStream());
			HSSFSheet sheetAt = wb.getSheetAt(0);
			int lastRowNum = sheetAt.getLastRowNum();
			for (int i = 1; i <= lastRowNum; i++) {
				HSSFRow row = sheetAt.getRow(i);
				Student s = new Student();
				String code = row.getCell(0).getStringCellValue();
				String name = row.getCell(1).getStringCellValue();
				String tname = row.getCell(2).getStringCellValue();
				Double numericCellValue = row.getCell(3).getNumericCellValue();
				int age = numericCellValue.intValue();
				String grade = row.getCell(4).getStringCellValue();
				Date entrytime = row.getCell(5).getDateCellValue();
				
				Integer id=  ss.findid(tname);
				
				s.setTeacherid(id);
				s.setName(name);
				s.setAge(age);
				s.setCode(code);
				s.setEntrytime(entrytime);
				s.setGrade(grade);
				ss.addStudent(s);
			}
			result.put("success", true);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导入用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	
}
