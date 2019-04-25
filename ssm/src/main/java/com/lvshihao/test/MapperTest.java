package com.lvshihao.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.lvshihao.dao.DepartmentMapper;
import com.lvshihao.dao.EmployeeMapper;
import com.lvshihao.entity.Employee;

/**
 * 测试dao层的工作
* 个性签名：今天的努力只要对得起自己就满足了
* 创建人：吕世昊  
* 创建时间：2019年4月14日 上午8:34:36   
* @version
* 推荐spring的项目使用Spring的单元测试,可以自动注入我们需要的组件
* 1.导入springtest模块
* 2.@ContextConfiguration指定spring配置文件的位置
* 3.直接autoWired使用的bean即可
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
public class MapperTest {
	@Autowired
	DepartmentMapper departmentMapper;
	@Autowired
	EmployeeMapper employeeMapper;
	@Test
	public void testCRUD() throws SQLException {
	//1.插入几个部门
//		departmentMapper.insertSelective(new Department(null,"开发"));
//		departmentMapper.insertSelective(new Department(null,"测试"));
	//2.生成员工数据,测试员工插入
		long currentTimeMillis = System.currentTimeMillis();
		ArrayList<Employee> employees=new ArrayList<>();
		for(int i=0;i<1000;i++) {
			String uid = UUID.randomUUID().toString().substring(0,5)+i;
			employees.add(new Employee(uid,"M",uid+"@lvhsihao",1));
		}
		employeeMapper.insertSelectiveBath(employees);
		System.out.println(System.currentTimeMillis()-currentTimeMillis);
		System.out.println("批量插入");
	}
}
