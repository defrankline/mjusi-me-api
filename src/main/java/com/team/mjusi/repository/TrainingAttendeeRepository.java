package com.team.mjusi.repository;

import com.team.mjusi.entity.TrainingAttendee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TrainingAttendeeRepository extends JpaRepository<TrainingAttendee, Long> {

    List<TrainingAttendee> findAllByUserId(Long userId);

    Optional<TrainingAttendee> findByUserIdAndTrainingId(Long userId, Long trainingId);

    int countAllByUserIdAndTrainingId(Long userId, Long trainingId);

    @Query("SELECT ur.training.name FROM TrainingAttendee ur WHERE ur.user.id =:userId")
    List<String> userTrainings(@Param("userId") Long userId);

    Page<TrainingAttendee> findAllByUserId(@Param("userId") Long userId, Pageable pageable);
}
