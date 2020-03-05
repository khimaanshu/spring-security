package com.example.springsecuritydemo.student;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
	private static final List<Student> STUDENTS=Arrays.asList(
			new Student(1, "Keith Gomes"),
			new Student(2, "Mercedez Fernandis"),
			new Student(3, "Himaanshu Shuklaa"),
			new Student(4, "Tiger"));
	
	@GetMapping(path="/{studentId}")
	public Student getStudent(@PathVariable("studentId") Integer studentId) throws IllegalAccessException
	{	
		return STUDENTS.stream()
				.filter(student->studentId.equals(student.getStudentId()))
				.findFirst()
				.orElseThrow(()->new IllegalAccessException(studentId+" not found."));
	}
	
	@GetMapping("/student")
	public String getStudent(){
		return "Hey Student!";
	}
	
	@PostMapping(path="/student/{studentName}")
	public String setStudent(@PathVariable("studentName") Integer studentName) {
		return "Hey! "+studentName;
	}
}
