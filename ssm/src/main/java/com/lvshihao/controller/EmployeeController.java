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
 * 处理员工CRUD请求 个性签名：今天的努力只要对得起自己就满足了 创建人：吕世昊 创建时间：2019年4月14日 上午11:02:42
 * 
 * @version
 */
@Controller
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;
	
	/**
	 * 单个批量2合一
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
	@ResponseBody
	public Msg deleteEmpById(@PathVariable("ids")String ids) {
		//批量删除
		if(ids.contains("-")) {
			List<Integer> list=new ArrayList<>();
			String[] split = ids.split("-");
			for (String string : split) {
				list.add(Integer.parseInt(string));
			}
			employeeService.deleteBatch(list);
		}else {
		//单个删除
			employeeService.deleteEmp(Integer.parseInt(ids));
		}
		return Msg.succes();
	}
	
	/**
	 * 根据id修改员工
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
	 * 根据id查询员工
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
	 * 检查用户名是否可用
	 * @param empName
	 * @return
	 */
	@RequestMapping("/checkuser")
	@ResponseBody
	public Msg chekuser(@RequestParam("empName")String empName) {
		//先判断用户名是否是合法的表达式
		String regx="(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
		if(!empName.matches(regx)) {
			return Msg.fail().add("va_msg", "用户名可以是2-5位或者6-16位英文和数字的组合");
		}
		//数据库用户名重复校验
		boolean b=employeeService.checkUser(empName);
		if(b) {
			return Msg.succes();
		}else {
			return Msg.fail().add("va_msg", "用户名不可用");
		}
	}
	
	/**
	 * 员工保存
	 * 1.支持JSR303校验
	 * 2.导入Hiberoate-Validator
	 * @param employee
	 * @return
	 */
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	@ResponseBody
	//这个@Valid是开启我们实体类里面的校验规则
	//BindingResult是校验之后的信息
	public Msg saveEmp(@Valid Employee employee,BindingResult bindingResult) {
		//判断如果有错误的话就不执行添加
		if(bindingResult.hasErrors()) {
			//校验失败,应该返回失败,在模态框中显示校验失败的信息
			Map<String, String> map=new HashMap<String, String>();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				System.out.println("错误的字段名:"+fieldError.getField());
				System.out.println("错误信息:"+fieldError.getDefaultMessage());
				map.put(fieldError.getField(),fieldError.getDefaultMessage());
			}
			return Msg.fail().add("FieldErrors", map);
		}else {
			employeeService.saveEmp(employee);
			return Msg.succes();
		}
		
	
	}
	/**
	 * 导入jackson包
	 * @param pn
	 * @return
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		// 在查询之前只需要调用,传入页码,以及每页的大小
		PageHelper.startPage(pn, 10);
		// startPage后面紧跟的这个就是一个分页查询
		List<Employee> all = employeeService.getAll();
		// 使用pageInfo包装查询后的结果,只需要将pageInfo交给页面就行了
		// 封装了详细的分页信息,包括我们查询出来的数据 
		PageInfo<Employee> pageInfo = new PageInfo<Employee>(all, 7);
		return Msg.succes().add("pageInfo", pageInfo);
	}

	/**
	 * 查询所有员工数据(分页查询)
	 * 
	 * @return
	 */
	// @RequestMapping("/emps")
	public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
		// 在查询之前只需要调用,传入页码,以及每页的大小
		PageHelper.startPage(pn, 5);
		// startPage后面紧跟的这个就是一个分页查询
		List<Employee> all = employeeService.getAll();
		// 使用pageInfo包装查询后的结果,只需要将pageInfo交给页面就行了
		// 封装了详细的分页信息,包括我们查询出来的数据
		PageInfo<Employee> pageInfo = new PageInfo<Employee>(all, 5);
		model.addAttribute("pageInfo", pageInfo);
		return "list";
	}
}
