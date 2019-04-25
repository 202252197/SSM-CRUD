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
 * ����Ͳ����йص�����
* ����ǩ���������Ŭ��ֻҪ�Ե����Լ���������
* �����ˣ������  
* ����ʱ�䣺2019��4��15�� ����4:55:01   
* @version
 */
@Controller
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	/**
	 * �������еĲ�����Ϣ
	 */
	@RequestMapping("/depts")
	@ResponseBody
	public Msg getDepts() {
		//������в�����Ϣ
		List<Department> list=departmentService.getDepts();
		return Msg.succes().add("depts", list);
	}
	
}
