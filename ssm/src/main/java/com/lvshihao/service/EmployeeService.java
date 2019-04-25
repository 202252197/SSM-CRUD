package com.lvshihao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvshihao.dao.EmployeeMapper;
import com.lvshihao.entity.Employee;
import com.lvshihao.entity.EmployeeExample;
import com.lvshihao.entity.EmployeeExample.Criteria;
@Service
public class EmployeeService {
	@Autowired
	EmployeeMapper employeeMapper;
	
	public List<Employee> getAll(){
		List<Employee> selectByExampleWithDept = employeeMapper.selectByExampleWithDept(null);
		return selectByExampleWithDept;
	}
	/**
	 * 员工保存方法
	 * @param employee
	 */
	public void saveEmp(Employee employee) {
		// TODO Auto-generated method stub
		employeeMapper.insertSelective(employee);
	}
	/**
	 * 检验用户名是否可用
	 * @param empName
	 * @return
	 */
	public boolean checkUser(String empName) {
		// TODO Auto-generated method stub
		EmployeeExample employeeExample=new EmployeeExample();
		Criteria createCriteria = employeeExample.createCriteria();
		 createCriteria.andEmpNameEqualTo(empName);
		 long countByExample = employeeMapper.countByExample(employeeExample);
		 return countByExample==0;
	}
	public Employee getEmp(Integer id) {
		// TODO Auto-generated method stub
		Employee selectByPrimaryKey = employeeMapper.selectByPrimaryKey(id);
		return selectByPrimaryKey;
	}
	/**
	 * 员工更新
	 * @param employee
	 */
	public void updateEmp(Employee employee) {
		// TODO Auto-generated method stub
		employeeMapper.updateByPrimaryKeySelective(employee);
	}
	public void deleteEmp(Integer id) {
		// TODO Auto-generated method stub
		employeeMapper.deleteByPrimaryKey(id);
	}
	public void deleteBatch(List<Integer> list) {
		// TODO Auto-generated method stub
		EmployeeExample employeeExample=new EmployeeExample();
		Criteria createCriteria = employeeExample.createCriteria();
		createCriteria.andEmpIdIn(list);
		employeeMapper.deleteByExample(employeeExample);
	}
	
}
