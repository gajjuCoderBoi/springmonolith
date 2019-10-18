package com.ga.service;

import com.ga.dao.CourseDao;
import com.ga.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseDao courseDao;

    @Override
    public Course createCourse(Course course) {
        return courseDao.createCourse(course);
    }

    @Override
    public List<Course> listCourses() {
        return courseDao.listCourses();
    }
}
