package com.lvshihao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lvshihao.entity.Department;
import com.lvshihao.entity.Msg;
import com.lvshihao.service.DepartmentService;

/**
 * 处理和部门有关的请求
* 个性签名：今天的努力只要对得起自己就满足了
* 创建人：吕世昊  
* 创建时间：2019年4月15日 下午4:55:01   
* @version
 */
@Controller
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	/**
	 * 返回所有的部门信息
	 */
	@RequestMapping("/depts")
	@ResponseBody
	public Msg getDepts() {
		//查出所有部门信息
		List<Department> list=departmentService.getDepts();
		return Msg.succes().add("depts", list);
	}
	
}
