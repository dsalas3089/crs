package com.example.crs.service;

import com.example.crs.model.Course;
import com.example.crs.model.Student;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface CourseService
{
    void viewAllCourses();
    void viewRegistrationReport();
    Long addCourse(@NotNull Course course);
    Optional<Course> getCourse( @NotBlank String courseName );
    List<Course> getCourses( );
    String registerStudentToCourse( @NotBlank String courseName, @NotNull Student student );
}
