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
 * ����dao��Ĺ���
* ����ǩ���������Ŭ��ֻҪ�Ե����Լ���������
* �����ˣ������  
* ����ʱ�䣺2019��4��14�� ����8:34:36   
* @version
* �Ƽ�spring����Ŀʹ��Spring�ĵ�Ԫ����,�����Զ�ע��������Ҫ�����
* 1.����springtestģ��
* 2.@ContextConfigurationָ��spring�����ļ���λ��
* 3.ֱ��autoWiredʹ�õ�bean����
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
	//1.���뼸������
//		departmentMapper.insertSelective(new Department(null,"����"));
//		departmentMapper.insertSelective(new Department(null,"����"));
	//2.����Ա������,����Ա������
		long currentTimeMillis = System.currentTimeMillis();
		ArrayList<Employee> employees=new ArrayList<>();
		for(int i=0;i<1000;i++) {
			String uid = UUID.randomUUID().toString().substring(0,5)+i;
			employees.add(new Employee(uid,"M",uid+"@lvhsihao",1));
		}
		employeeMapper.insertSelectiveBath(employees);
		System.out.println(System.currentTimeMillis()-currentTimeMillis);
		System.out.println("��������");
	}
}
