package com.team.mjusi.service.impl;

import com.team.mjusi.entity.UserRole;
import com.team.mjusi.repository.UserRoleRepository;
import com.team.mjusi.service.UserRoleService;
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
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository repository;

    @Override
    public List<UserRole> findAllByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public Optional<UserRole> findByUserIdAndRoleId(Long userId, Long roleId) {
        return repository.findByUserIdAndRoleId(userId, roleId);
    }

    @Override
    public int countAllByUserIdAndRoleId(Long userId, Long roleId) {
        return repository.countAllByUserIdAndRoleId(userId, roleId);
    }

    @Override
    public List<String> userRoles(Long userId) {
        return repository.userRoles(userId);
    }

    @Override
    public Page<UserRole> findAllByUserId(Long userId, Pageable pageable) {
        return repository.findAllByUserId(userId, pageable);
    }

    @Override
    public UserRole save(UserRole userRole) {
        return repository.save(userRole);
    }

    @Override
    public Optional<UserRole> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
