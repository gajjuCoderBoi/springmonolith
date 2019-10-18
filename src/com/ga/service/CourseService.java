package com.ga.service;

import com.ga.entity.Course;

import java.util.List;

public interface CourseService {
    public Course createCourse(Course course);

    public List<Course> listCourses();
}