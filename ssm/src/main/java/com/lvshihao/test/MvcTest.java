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
	//传入springmvc的ioc
	@Autowired
	WebApplicationContext context;
	//虚拟mvc请求,获取到处理结果
	MockMvc mockMvc;
	@Before
	public void initMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	@Test
	public void testPage() throws Exception {
		//模拟请求拿到返回值
		MvcResult andReturn = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "1")).andReturn();
		//请求成功以后,请求域中会有pageinfo,我们可以取出pageinfo进行验证
		MockHttpServletRequest request = andReturn.getRequest();
		@SuppressWarnings("unchecked")
		PageInfo<Employee> pageinfo = (PageInfo<Employee>) request.getAttribute("pageInfo");
		System.out.println("当前页码:"+pageinfo.getPageNum());
		System.out.println("总页码:"+pageinfo.getPages());
		System.out.println("总记录数:"+pageinfo.getTotal());
		System.out.println("在页面需要连续显示的页码");
		int[] navigatepageNums = pageinfo.getNavigatepageNums();
		for (int i : navigatepageNums) {
			System.out.println(" "+i);
		}
		//获取员工数据
		List<Employee> list=pageinfo.getList();
		list.forEach(System.out::println);
	}
}
