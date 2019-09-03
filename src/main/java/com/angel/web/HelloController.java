package com.angel.web;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.angel.bean.Student;
import com.angel.bean.User;
import com.angel.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 浮生
 * @date 2019/3/14 15:22
 */
@RestController
public class HelloController
{
	@Autowired
	private StudentService studentService;
	
	@RequestMapping("/hello")
	@SentinelResource("hello")
	public String index()
	{
		return "hello world!";
	}
	
	@RequestMapping(value = "/querystudent", method = RequestMethod.GET)
	public Student queryStudentBySno(String sno)
	{
		return this.studentService.queryStudentBySno(sno);
	}
	
	@RequestMapping(value = "/test",method = RequestMethod.POST)
	public String test(Student student, @RequestBody  User user)
	{
		System.out.println(student.getSno());
		System.out.println(user.getName());
		return student.toString();
	}
}
