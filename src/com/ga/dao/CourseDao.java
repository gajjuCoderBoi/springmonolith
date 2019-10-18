package com.ga.dao;

import com.ga.entity.Course;

import java.util.List;

public interface CourseDao {
    public Course createCourse(Course course);

    public List<Course> listCourses();
}
