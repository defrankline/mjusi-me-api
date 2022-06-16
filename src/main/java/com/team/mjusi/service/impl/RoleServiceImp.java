package com.team.mjusi.service.impl;

import com.team.mjusi.entity.Role;
import com.team.mjusi.repository.RoleRepository;
import com.team.mjusi.service.RoleService;
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
public class RoleServiceImp implements RoleService {
    private final RoleRepository repository;

    @Override
    public List<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Role save(Role role) {
        return repository.save(role);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return repository.findFirstByName(name);
    }

    @Override
    public List<Role> findAllExcept(List<Long> exceptIds) {
        return repository.findAllExcept(exceptIds);
    }

    @Override
    public List<Role> findAllIds(List<Long> ids) {
        return repository.findAllIds(ids);
    }
}
