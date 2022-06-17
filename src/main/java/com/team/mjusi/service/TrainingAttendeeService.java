package com.team.mjusi.service;

import com.team.mjusi.entity.TrainingAttendee;
import com.team.mjusi.entity.dto.MaleFemaleDto;
import com.team.mjusi.entity.dto.MaleFemaleWrapperDto;
import com.team.mjusi.entity.dto.UserTrainingCreateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface TrainingAttendeeService {
    List<TrainingAttendee> findAll();

    Page<TrainingAttendee> findAll(Pageable pageable);

    List<TrainingAttendee> findAllByUserId(Long userId);

    Page<TrainingAttendee> findAllByUserId(Long userId, Pageable pageable);

    List<TrainingAttendee> findAllByTrainingId(Long trainingId);

    Page<TrainingAttendee> findAllByTrainingId(Long trainingId, Pageable pageable);

    Optional<TrainingAttendee> findFirstByUserIdAndTrainingId(Long userId, Long trainingId);

    List<TrainingAttendee> save(UserTrainingCreateDto createDto);

    TrainingAttendee save(TrainingAttendee trainingAttendee);

    Optional<TrainingAttendee> findById(Long id);

    void delete(Long id);

    MaleFemaleDto reportAttendeeByGender();

    MaleFemaleDto reportStudentByGender();

    MaleFemaleWrapperDto reportByGender();
}
