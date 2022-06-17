package com.team.mjusi.service;

import com.team.mjusi.entity.Training;
import com.team.mjusi.entity.dto.TrainingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface TrainingService {
    List<Training> findAll();

    Page<Training> findAll(Pageable pageable);

    Training save(Training training);

    void delete(Long id);

    Optional<Training> findById(Long id);

}
