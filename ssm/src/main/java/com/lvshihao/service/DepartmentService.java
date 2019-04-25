package com.lvshihao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvshihao.dao.DepartmentMapper;
import com.lvshihao.entity.Department;
@Service
public class DepartmentService {
	@Autowired
	private DepartmentMapper departmentMapper;
	public List<Department> getDepts() {
		// TODO Auto-generated method stub
		List<Department> selectByExample = departmentMapper.selectByExample(null);
		return selectByExample;
	}
}
