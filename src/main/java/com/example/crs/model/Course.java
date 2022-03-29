package com.example.crs.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course implements Serializable
{

    private static final long serialVersionUID = -4426507889773017025L;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;

    private String name;

    @Column( name = "max_students" )
    private Integer maxStudents;

    @Column( name = "current_students" )
    private Integer currentStudents;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "student_course",
            joinColumns = { @JoinColumn(name = "course_id") },
            inverseJoinColumns = { @JoinColumn(name = "student_id") })
    private Set<Student> students = new HashSet<>();

    public Course( String name, Integer maxStudents, Integer currentStudents )
    {
        this.name = name;
        this.maxStudents = maxStudents;
        this.currentStudents = currentStudents;
    }

    public Course()
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

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Integer getMaxStudents()
    {
        return maxStudents;
    }

    public void setMaxStudents( Integer maxStudents )
    {
        this.maxStudents = maxStudents;
    }

    public Integer getCurrentStudents()
    {
        return currentStudents;
    }

    public void setCurrentStudents( Integer currentStudents )
    {
        this.currentStudents = currentStudents;
    }

    public Set<Student> getStudents()
    {
        return students;
    }

    public void setStudents( Set<Student> students )
    {
        this.students = students;
    }

    public void addStudent( Student student )
    {
        this.students.add( student );
        student.getCourses().add( this );
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;
        Course course = ( Course ) o;
        return Objects.equals( id, course.id ) && Objects.equals( name, course.name ) && Objects.equals( maxStudents, course.maxStudents ) && Objects.equals( currentStudents, course.currentStudents );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( id, name, maxStudents, currentStudents );
    }

    public String print(boolean isReport)
    {
        if (isReport) {
            String names = "";
            for(Student student :students) {
                String addFirst = student.getFirstName();
                String addLast = student.getLastName();
                names = names + addFirst + " " + addLast + ", ";
            }
            System.out.println("Course: " + name + "\n" + "Course ID: " + id + "\n"
                    + "Maximum # of Students: " + maxStudents + "\n" + "Current # of Students: " + currentStudents
                    + "\n" + "Registered Students: " + names );
            System.out.println("==========");
            String text1 = "Course: " + name + "\n" + "Course ID: " + id + "\n" + "Maximum # of Students: "
                    + maxStudents + "\n" + "Current # of Students: " + currentStudents + "\n" + "Registered Students: "
                    + names;
            return (text1);
        } else
        {
            System.out.println( "Course: " + name + "\n" + "Course ID: " + id + "\n"
                    + "Maximum # of Students: " + maxStudents );
            System.out.println( "==========" );
            String text2 = "Course: " + name + "\n" + "Course ID: " + id + "\n" + "Maximum # of Students: "
                    + maxStudents + "\n" + "Current # of Students: " + currentStudents;
            return ( text2 );
        }
    }

}
