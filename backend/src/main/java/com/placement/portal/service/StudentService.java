package com.placement.portal.service;

import com.placement.portal.dto.StudentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {
    StudentDto createStudent(StudentDto studentDto);
    StudentDto getStudentById(Long id);
    StudentDto updateStudent(Long id, StudentDto studentDto);
    void deleteStudent(Long id);
    Page<StudentDto> searchStudents(String keyword, String status, Pageable pageable);
}
