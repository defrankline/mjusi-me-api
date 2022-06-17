package com.team.mjusi.repository;

import com.team.mjusi.entity.TrainingAttendee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TrainingAttendeeRepository extends JpaRepository<TrainingAttendee, Long> {

    List<TrainingAttendee> findAllByUserId(Long userId);

    Page<TrainingAttendee> findAllByUserId(Long userId, Pageable pageable);

    List<TrainingAttendee> findAllByTrainingId(Long trainingId);

    Page<TrainingAttendee> findAllByTrainingId(Long trainingId, Pageable pageable);

    Optional<TrainingAttendee> findFirstByUserIdAndTrainingId(Long userId, Long trainingId);
}
