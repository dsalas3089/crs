package com.example.crs.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
public class Student implements Serializable
{
    private static final long serialVersionUID = -5382762200916454478L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public Student( String firstName, String lastName )
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @ManyToMany( fetch = FetchType.EAGER, mappedBy = "students")
    private Set<Course> courses = new HashSet<>();

    public Student()
    {

    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public Set<Course> getCourses()
    {
        return courses;
    }

    public void setCourses( Set<Course> courses )
    {
        this.courses = courses;
    }

    public void addCourse( Course course )
    {
        this.courses.add( course );
        course.getStudents().add( this );

    }


    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;
        Student student = ( Student ) o;
        return Objects.equals( id, student.id ) && Objects.equals( firstName, student.firstName ) && Objects.equals( lastName, student.lastName );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( id, firstName, lastName);
    }
}
