package com.example.crs.service;

import com.example.crs.model.Course;
import com.example.crs.model.Student;
import com.example.crs.repository.CourseRepository;
import com.example.crs.repository.StudentRepository;
import com.example.crs.service.impl.CourseServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

public class CourseServiceTest
{

    @Mock
    private CourseRepository courseRepositoryMock;

    @Mock
    private StudentRepository StudentRepositoryMock;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks( this );
    }

    @Test
    public void testAddCourse()
    {
        Course newCourse = new Course( "Test name", 5, 0 );
        newCourse.setId( 1L );
        Mockito.when( courseRepositoryMock.save( Mockito.any( Course.class ) ) ).thenReturn( newCourse );
        Long courseId = courseService.addCourse( newCourse );
        Assert.assertEquals( 1L, courseId.longValue() );
    }

    @Test
    public void testGetCourseName()
    {
        String courseName = "Test";
        Optional<Course> result = courseService.getCourse( courseName );
        Assert.assertTrue( result.isEmpty() );
    }

    @Test
    public void testRegisterStudentToClosedCourse()
    {
        Long courseId = 1l;
        Course course = new Course();
        course.setId( courseId );
        course.setName( "English 1" );
        course.setMaxStudents( 2 );
        course.setCurrentStudents( 2 );

        course.setStudents( Collections.emptySet() );
        Mockito.when( courseRepositoryMock.findCourseByNameIgnoreCase( Mockito.anyString() ) ).thenReturn( Optional.of( course ) );
        String result = courseService.registerStudentToCourse( course.getName(), new Student() );
        Assert.assertEquals( "This course is closed for registration", result );
    }

    @Test
    public void testRegisterStudentToCourseNotFound()
    {
        Long courseId = 1l;
        Course course = new Course();
        course.setId( courseId );
        course.setName( "English 1" );
        course.setMaxStudents( 2 );
        course.setCurrentStudents( 2 );

        course.setStudents( Collections.emptySet() );
        Mockito.when( courseRepositoryMock.findCourseByNameIgnoreCase( Mockito.anyString() ) ).thenReturn( Optional.empty() );
        String result = courseService.registerStudentToCourse( course.getName(), new Student() );
        Assert.assertEquals( "Wrong course name", result );
    }

    @Test
    public void testRegisterStudentCourseSucceed()
    {
        Course course = new Course();
        course.setId( 1L );
        course.setName( "English 1" );
        course.setMaxStudents( 2 );
        course.setCurrentStudents( 1 );

        Student student = new Student();
        student.setFirstName( "David" );

        Mockito.when( courseRepositoryMock.findCourseByNameIgnoreCase( Mockito.anyString() ) ).thenReturn( Optional.of( course ) );
        Mockito.when( courseRepositoryMock.save( Mockito.any( Course.class ) ) ).thenReturn( course );
        String result = courseService.registerStudentToCourse( course.getName(), student );
        Assert.assertEquals( "Registration succeeded", result );
    }
}
