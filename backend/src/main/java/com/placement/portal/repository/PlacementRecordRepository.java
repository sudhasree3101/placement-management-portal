package com.placement.portal.repository;

import com.placement.portal.entity.PlacementRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlacementRecordRepository extends JpaRepository<PlacementRecord, Long> {
    List<PlacementRecord> findByStudentId(Long studentId);
}
