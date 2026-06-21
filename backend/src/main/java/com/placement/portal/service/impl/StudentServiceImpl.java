package com.placement.portal.service.impl;

import com.placement.portal.dto.StudentDto;
import com.placement.portal.entity.PlacementStatus;
import com.placement.portal.entity.Role;
import com.placement.portal.entity.Student;
import com.placement.portal.entity.User;
import com.placement.portal.exception.DuplicateResourceException;
import com.placement.portal.exception.ResourceNotFoundException;
import com.placement.portal.mapper.DtoMapper;
import com.placement.portal.repository.StudentRepository;
import com.placement.portal.repository.UserRepository;
import com.placement.portal.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public StudentDto createStudent(StudentDto studentDto) {
        if (studentRepository.findByEmail(studentDto.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Student with email " + studentDto.getEmail() + " already exists");
        }

        Student student = DtoMapper.toStudentEntity(studentDto);
        
        User user = User.builder()
                .name(student.getName())
                .email(student.getEmail())
                .password(passwordEncoder.encode(student.getName() + "123"))
                .role(Role.STUDENT)
                .enabled(true)
                .build();
        
        student.setUser(user);
        Student savedStudent = studentRepository.save(student);
        return DtoMapper.toStudentDto(savedStudent);
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        return DtoMapper.toStudentDto(student);
    }

    @Override
    @Transactional
    public StudentDto updateStudent(Long id, StudentDto studentDto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));

        student.setName(studentDto.getName());
        student.setPhone(studentDto.getPhone());
        student.setDepartment(studentDto.getDepartment());
        student.setCgpa(studentDto.getCgpa());
        student.setSkills(studentDto.getSkills());
        student.setGraduationYear(studentDto.getGraduationYear());
        student.setResumeUrl(studentDto.getResumeUrl());
        
        if (studentDto.getPlacementStatus() != null) {
            student.setPlacementStatus(PlacementStatus.valueOf(studentDto.getPlacementStatus()));
        }

        if (student.getUser() != null) {
            student.getUser().setName(studentDto.getName());
        }

        Student updatedStudent = studentRepository.save(student);
        return DtoMapper.toStudentDto(updatedStudent);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        studentRepository.delete(student);
    }

    @Override
    public Page<StudentDto> searchStudents(String keyword, String status, Pageable pageable) {
        PlacementStatus placementStatus = null;
        if (status != null && !status.trim().isEmpty()) {
            try {
                placementStatus = PlacementStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Ignore invalid status
            }
        }
        
        String cleanKeyword = (keyword == null || keyword.trim().isEmpty()) ? null : keyword.trim();
        Page<Student> studentsPage = studentRepository.searchStudents(cleanKeyword, placementStatus, pageable);
        return studentsPage.map(DtoMapper::toStudentDto);
    }
}
