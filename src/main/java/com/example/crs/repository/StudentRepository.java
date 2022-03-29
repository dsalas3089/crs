package com.example.crs.repository;


import com.example.crs.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository  extends JpaRepository<Student, Long>
{
    Optional<Student> findStudentByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
}
