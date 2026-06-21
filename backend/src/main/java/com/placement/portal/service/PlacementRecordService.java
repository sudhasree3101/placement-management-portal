package com.placement.portal.service;

import com.placement.portal.dto.PlacementRecordDto;

import java.util.List;

public interface PlacementRecordService {
    PlacementRecordDto createRecord(PlacementRecordDto recordDto);
    PlacementRecordDto getRecordById(Long id);
    PlacementRecordDto updateRecord(Long id, PlacementRecordDto recordDto);
    void deleteRecord(Long id);
    List<PlacementRecordDto> getRecordsByStudentId(Long studentId);
    List<PlacementRecordDto> getAllRecords();
}
