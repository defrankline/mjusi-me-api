package com.team.mjusi.service.impl;


import com.team.mjusi.entity.Training;
import com.team.mjusi.repository.TrainingRepository;
import com.team.mjusi.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository repository;

    @Override
    public List<Training> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Training> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Training save(Training training) {
        return repository.save(training);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Training> findById(Long id) {
        return repository.findById(id);
    }
}
