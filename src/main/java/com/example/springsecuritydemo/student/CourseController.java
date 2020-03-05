package com.example.springsecuritydemo.student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/courses")
public class CourseController {
	@GetMapping("/course")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STUDENT')")
	public String getCourse(){
		return "I am Course API!";
	}
	
	@PostMapping(path="/course/{courseName}")
	@PreAuthorize("hasAuthority('student:write')")
	public String setCourse(@PathVariable("courseName") Integer courseName) {
		return "Course Name: "+courseName;
	}
}
