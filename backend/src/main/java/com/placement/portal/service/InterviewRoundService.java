package com.placement.portal.service;

import com.placement.portal.dto.InterviewRoundDto;

import java.util.List;

public interface InterviewRoundService {
    InterviewRoundDto createRound(InterviewRoundDto roundDto);
    InterviewRoundDto getRoundById(Long id);
    InterviewRoundDto updateRound(Long id, InterviewRoundDto roundDto);
    void deleteRound(Long id);
    List<InterviewRoundDto> getRoundsByApplicationId(Long applicationId);
}
