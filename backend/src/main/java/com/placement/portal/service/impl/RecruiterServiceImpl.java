package com.placement.portal.service.impl;

import com.placement.portal.dto.RecruiterDto;
import com.placement.portal.entity.Recruiter;
import com.placement.portal.entity.Role;
import com.placement.portal.entity.User;
import com.placement.portal.exception.DuplicateResourceException;
import com.placement.portal.exception.ResourceNotFoundException;
import com.placement.portal.mapper.DtoMapper;
import com.placement.portal.repository.RecruiterRepository;
import com.placement.portal.repository.UserRepository;
import com.placement.portal.service.RecruiterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruiterServiceImpl implements RecruiterService {

    private final RecruiterRepository recruiterRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public RecruiterDto createRecruiter(RecruiterDto recruiterDto) {
        if (recruiterRepository.findByEmail(recruiterDto.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Recruiter with email " + recruiterDto.getEmail() + " already exists");
        }

        Recruiter recruiter = DtoMapper.toRecruiterEntity(recruiterDto);
        
        User user = User.builder()
                .name(recruiter.getHrName())
                .email(recruiter.getEmail())
                .password(passwordEncoder.encode(recruiter.getCompanyName() + "HR"))
                .role(Role.RECRUITER)
                .enabled(true)
                .build();
        
        recruiter.setUser(user);
        Recruiter savedRecruiter = recruiterRepository.save(recruiter);
        return DtoMapper.toRecruiterDto(savedRecruiter);
    }

    @Override
    public RecruiterDto getRecruiterById(Long id) {
        Recruiter recruiter = recruiterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter not found with ID: " + id));
        return DtoMapper.toRecruiterDto(recruiter);
    }

    @Override
    @Transactional
    public RecruiterDto updateRecruiter(Long id, RecruiterDto recruiterDto) {
        Recruiter recruiter = recruiterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter not found with ID: " + id));

        recruiter.setCompanyName(recruiterDto.getCompanyName());
        recruiter.setHrName(recruiterDto.getHrName());
        recruiter.setPhone(recruiterDto.getPhone());
        recruiter.setWebsite(recruiterDto.getWebsite());
        recruiter.setIndustry(recruiterDto.getIndustry());

        if (recruiter.getUser() != null) {
            recruiter.getUser().setName(recruiterDto.getHrName());
        }

        Recruiter updatedRecruiter = recruiterRepository.save(recruiter);
        return DtoMapper.toRecruiterDto(updatedRecruiter);
    }

    @Override
    @Transactional
    public void deleteRecruiter(Long id) {
        Recruiter recruiter = recruiterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter not found with ID: " + id));
        recruiterRepository.delete(recruiter);
    }

    @Override
    public List<RecruiterDto> getAllRecruiters() {
        return recruiterRepository.findAll().stream()
                .map(DtoMapper::toRecruiterDto)
                .collect(Collectors.toList());
    }
}
