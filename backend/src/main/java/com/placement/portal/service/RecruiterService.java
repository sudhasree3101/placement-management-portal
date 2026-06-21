package com.placement.portal.service;

import com.placement.portal.dto.RecruiterDto;

import java.util.List;

public interface RecruiterService {
    RecruiterDto createRecruiter(RecruiterDto recruiterDto);
    RecruiterDto getRecruiterById(Long id);
    RecruiterDto updateRecruiter(Long id, RecruiterDto recruiterDto);
    void deleteRecruiter(Long id);
    List<RecruiterDto> getAllRecruiters();
}
