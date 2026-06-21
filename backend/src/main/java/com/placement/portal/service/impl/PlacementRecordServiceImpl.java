package com.placement.portal.service.impl;

import com.placement.portal.dto.PlacementRecordDto;
import com.placement.portal.entity.PlacementRecord;
import com.placement.portal.entity.PlacementStatus;
import com.placement.portal.entity.Student;
import com.placement.portal.exception.ResourceNotFoundException;
import com.placement.portal.mapper.DtoMapper;
import com.placement.portal.repository.PlacementRecordRepository;
import com.placement.portal.repository.StudentRepository;
import com.placement.portal.service.PlacementRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlacementRecordServiceImpl implements PlacementRecordService {

    private final PlacementRecordRepository recordRepository;
    private final StudentRepository studentRepository;

    @Override
    @Transactional
    public PlacementRecordDto createRecord(PlacementRecordDto dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + dto.getStudentId()));

        PlacementRecord record = PlacementRecord.builder()
                .student(student)
                .companyName(dto.getCompanyName())
                .jobRole(dto.getJobRole())
                .packageAmount(dto.getPackageAmount())
                .joiningDate(dto.getJoiningDate())
                .build();

        PlacementRecord savedRecord = recordRepository.save(record);

        student.setPlacementStatus(PlacementStatus.PLACED);
        studentRepository.save(student);

        return DtoMapper.toPlacementRecordDto(savedRecord);
    }

    @Override
    public PlacementRecordDto getRecordById(Long id) {
        PlacementRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Placement record not found with ID: " + id));
        return DtoMapper.toPlacementRecordDto(record);
    }

    @Override
    @Transactional
    public PlacementRecordDto updateRecord(Long id, PlacementRecordDto dto) {
        PlacementRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Placement record not found with ID: " + id));

        record.setCompanyName(dto.getCompanyName());
        record.setJobRole(dto.getJobRole());
        record.setPackageAmount(dto.getPackageAmount());
        record.setJoiningDate(dto.getJoiningDate());

        PlacementRecord updatedRecord = recordRepository.save(record);
        return DtoMapper.toPlacementRecordDto(updatedRecord);
    }

    @Override
    @Transactional
    public void deleteRecord(Long id) {
        PlacementRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Placement record not found with ID: " + id));
        recordRepository.delete(record);
    }

    @Override
    public List<PlacementRecordDto> getRecordsByStudentId(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with ID: " + studentId);
        }
        return recordRepository.findByStudentId(studentId).stream()
                .map(DtoMapper::toPlacementRecordDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlacementRecordDto> getAllRecords() {
        return recordRepository.findAll().stream()
                .map(DtoMapper::toPlacementRecordDto)
                .collect(Collectors.toList());
    }
}
