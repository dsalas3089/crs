package com.example.crs.service.impl;

import com.example.crs.model.Course;
import com.example.crs.model.Student;
import com.example.crs.repository.CourseRepository;
import com.example.crs.repository.StudentRepository;
import com.example.crs.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class CourseServiceImpl implements CourseService
{

    private final static Logger LOG = LoggerFactory.getLogger( CourseService.class );

    private final static String SUCCEED_MESSAGE = "Registration succeeded";

    private final static String FAIL_MESSAGE = "Error registering student";

    private final static String CLOSED_COURSE_MESSAGE = "This course is closed for registration";

    private final static String WRONG_COURSE_MESSAGE = "Wrong course name";

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public CourseServiceImpl( CourseRepository courseRepository, StudentRepository studentRepository )
    {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void viewAllCourses()
    {
        this.getCourses().forEach( course -> course.print(Boolean.FALSE) );
    }

    @Override
    public void viewRegistrationReport()
    {
        this.getCourses().forEach( course -> course.print(Boolean.TRUE) );
    }

    @Override
    public Long addCourse( Course course )
    {
        course = this.courseRepository.save( course );
        LOG.info( "Course: {} has been successfully added. ", course.getId() );
        return course.getId();
    }

    @Override
    public Optional<Course> getCourse( String courseName )
    {
        return this.courseRepository.findCourseByNameIgnoreCase( courseName );
    }

    @Override
    public List<Course> getCourses()
    {
        return this.courseRepository.findAll()
                .stream()
                .filter( course -> course.getCurrentStudents() < course.getMaxStudents() )
                .collect( Collectors.toList() );
    }

    @Override
    public String registerStudentToCourse( String name, Student student )
    {
        Student newStudent = studentRepository.findStudentByFirstNameIgnoreCaseAndLastNameIgnoreCase( student.getFirstName(),student.getLastName() ).orElse( student );
        LOG.info( "Course Name :: {} , Student :: {}", name, student );
        Optional<Course> courseOpt = this.courseRepository.findCourseByNameIgnoreCase( name );
        if ( courseOpt.isEmpty() )
            return WRONG_COURSE_MESSAGE;
        Course course = courseOpt.get();
        if ( course.getCurrentStudents() == course.getMaxStudents() )
            return CLOSED_COURSE_MESSAGE;
        course.setCurrentStudents( course.getCurrentStudents() + 1 );
        newStudent.addCourse( course );
        course.addStudent( newStudent );
        return Objects.nonNull( this.courseRepository.save( course ).getId() ) ? SUCCEED_MESSAGE : FAIL_MESSAGE;
    }
}
