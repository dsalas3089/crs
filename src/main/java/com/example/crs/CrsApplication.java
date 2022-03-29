package com.example.crs;

import com.example.crs.model.Student;
import com.example.crs.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class CrsApplication implements CommandLineRunner
{
	@Autowired
	private CourseService courseService;

	public static void main(String[] args) {
		SpringApplication.run(CrsApplication.class, args);
	}


	@Override
	public void run( String... args ) throws IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Welcome to the Registry Course System!!");

		boolean logout = false;
		while (!logout)
		{
			System.out.println("Please select the option to proceed?");
			System.out.println("Enter '1' to view courses");
			System.out.println("Enter '2' to register a student");
			System.out.println("Enter '3' to Exit");
			String option = in.readLine();

			if ( option.contentEquals( "1" ) )
			{
				this.courseService.viewAllCourses();
			}
			else if ( option.contentEquals( "2" ) )
			{
				System.out.println( "Enter the new student's first name: " );
				String firstName = in.readLine();
				System.out.println( "Enter the new student's last name: " );
				String lastName = in.readLine();
				Student newStudent = new Student( firstName, lastName );
				System.out.println( "Course name: " );
				String courseName = in.readLine();
				String message = this.courseService.registerStudentToCourse( courseName, newStudent );
				System.out.println( message );
				System.out.println( " " );
				this.courseService.viewRegistrationReport();
			}
			else
			{
				System.out.println("Thank you & come again!`");
				logout = true;
				in.close();
			}
		}





	}
}
