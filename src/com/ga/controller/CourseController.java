package com.ga.controller;

import com.ga.entity.Course;
import com.ga.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping("/list")
    public List<Course> getCourses(){
        return courseService.listCourses();
    }

    @PostMapping("/")
    public Course createCourse(Course course){
        return courseService.createCourse(course);
    }
}
