package com.lvshihao.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.pagehelper.PageInfo;
import com.lvshihao.entity.Employee;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations= {"classpath:applicationContext.xml","classpath:spring-mvc.xml"})
public class MvcTest {
	//����springmvc��ioc
	@Autowired
	WebApplicationContext context;
	//����mvc����,��ȡ��������
	MockMvc mockMvc;
	@Before
	public void initMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	@Test
	public void testPage() throws Exception {
		//ģ�������õ�����ֵ
		MvcResult andReturn = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "1")).andReturn();
		//����ɹ��Ժ�,�������л���pageinfo,���ǿ���ȡ��pageinfo������֤
		MockHttpServletRequest request = andReturn.getRequest();
		@SuppressWarnings("unchecked")
		PageInfo<Employee> pageinfo = (PageInfo<Employee>) request.getAttribute("pageInfo");
		System.out.println("��ǰҳ��:"+pageinfo.getPageNum());
		System.out.println("��ҳ��:"+pageinfo.getPages());
		System.out.println("�ܼ�¼��:"+pageinfo.getTotal());
		System.out.println("��ҳ����Ҫ������ʾ��ҳ��");
		int[] navigatepageNums = pageinfo.getNavigatepageNums();
		for (int i : navigatepageNums) {
			System.out.println(" "+i);
		}
		//��ȡԱ������
		List<Employee> list=pageinfo.getList();
		list.forEach(System.out::println);
	}
}
