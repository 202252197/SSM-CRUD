package com.lvshihao.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lvshihao.entity.Employee;
import com.lvshihao.entity.Msg;
import com.lvshihao.service.EmployeeService;

/**
 * ����Ա��CRUD���� ����ǩ���������Ŭ��ֻҪ�Ե����Լ��������� �����ˣ������ ����ʱ�䣺2019��4��14�� ����11:02:42
 * 
 * @version
 */
@Controller
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;
	
	/**
	 * ��������2��һ
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
	@ResponseBody
	public Msg deleteEmpById(@PathVariable("ids")String ids) {
		//����ɾ��
		if(ids.contains("-")) {
			List<Integer> list=new ArrayList<>();
			String[] split = ids.split("-");
			for (String string : split) {
				list.add(Integer.parseInt(string));
			}
			employeeService.deleteBatch(list);
		}else {
		//����ɾ��
			employeeService.deleteEmp(Integer.parseInt(ids));
		}
		return Msg.succes();
	}
	
	/**
	 * ����id�޸�Ա��
	 * @param id
	 * @param employee
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{id}",method=RequestMethod.PUT)
	public Msg SaveEmp(@PathVariable("id") Integer id,Employee employee) {
		employee.setEmpId(id);
		employeeService.updateEmp(employee);
		return Msg.succes();
	}
	/**
	 * ����id��ѯԱ��
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Msg getEmp(@PathVariable("id")Integer id) {
		Employee employee=employeeService.getEmp(id);
		System.out.println(employee);
		return Msg.succes().add("emp", employee);
	}
	/**
	 * ����û����Ƿ����
	 * @param empName
	 * @return
	 */
	@RequestMapping("/checkuser")
	@ResponseBody
	public Msg chekuser(@RequestParam("empName")String empName) {
		//���ж��û����Ƿ��ǺϷ��ı��ʽ
		String regx="(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
		if(!empName.matches(regx)) {
			return Msg.fail().add("va_msg", "�û���������2-5λ����6-16λӢ�ĺ����ֵ����");
		}
		//���ݿ��û����ظ�У��
		boolean b=employeeService.checkUser(empName);
		if(b) {
			return Msg.succes();
		}else {
			return Msg.fail().add("va_msg", "�û���������");
		}
	}
	
	/**
	 * Ա������
	 * 1.֧��JSR303У��
	 * 2.����Hiberoate-Validator
	 * @param employee
	 * @return
	 */
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	@ResponseBody
	//���@Valid�ǿ�������ʵ���������У�����
	//BindingResult��У��֮�����Ϣ
	public Msg saveEmp(@Valid Employee employee,BindingResult bindingResult) {
		//�ж�����д���Ļ��Ͳ�ִ�����
		if(bindingResult.hasErrors()) {
			//У��ʧ��,Ӧ�÷���ʧ��,��ģ̬������ʾУ��ʧ�ܵ���Ϣ
			Map<String, String> map=new HashMap<String, String>();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				System.out.println("������ֶ���:"+fieldError.getField());
				System.out.println("������Ϣ:"+fieldError.getDefaultMessage());
				map.put(fieldError.getField(),fieldError.getDefaultMessage());
			}
			return Msg.fail().add("FieldErrors", map);
		}else {
			employeeService.saveEmp(employee);
			return Msg.succes();
		}
		
	
	}
	/**
	 * ����jackson��
	 * @param pn
	 * @return
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		// �ڲ�ѯ֮ǰֻ��Ҫ����,����ҳ��,�Լ�ÿҳ�Ĵ�С
		PageHelper.startPage(pn, 10);
		// startPage����������������һ����ҳ��ѯ
		List<Employee> all = employeeService.getAll();
		// ʹ��pageInfo��װ��ѯ��Ľ��,ֻ��Ҫ��pageInfo����ҳ�������
		// ��װ����ϸ�ķ�ҳ��Ϣ,�������ǲ�ѯ���������� 
		PageInfo<Employee> pageInfo = new PageInfo<Employee>(all, 7);
		return Msg.succes().add("pageInfo", pageInfo);
	}

	/**
	 * ��ѯ����Ա������(��ҳ��ѯ)
	 * 
	 * @return
	 */
	// @RequestMapping("/emps")
	public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
		// �ڲ�ѯ֮ǰֻ��Ҫ����,����ҳ��,�Լ�ÿҳ�Ĵ�С
		PageHelper.startPage(pn, 5);
		// startPage����������������һ����ҳ��ѯ
		List<Employee> all = employeeService.getAll();
		// ʹ��pageInfo��װ��ѯ��Ľ��,ֻ��Ҫ��pageInfo����ҳ�������
		// ��װ����ϸ�ķ�ҳ��Ϣ,�������ǲ�ѯ����������
		PageInfo<Employee> pageInfo = new PageInfo<Employee>(all, 5);
		model.addAttribute("pageInfo", pageInfo);
		return "list";
	}
}
