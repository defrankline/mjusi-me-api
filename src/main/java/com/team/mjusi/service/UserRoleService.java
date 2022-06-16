package com.team.mjusi.service;

import com.team.mjusi.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface UserRoleService {

    List<UserRole> findAllByUserId(Long userId);

    Optional<UserRole> findByUserIdAndRoleId(Long userId, Long roleId);

    int countAllByUserIdAndRoleId(Long userId, Long roleId);

    List<String> userRoles(Long userId);

    Page<UserRole> findAllByUserId(Long userId, Pageable pageable);

    UserRole save(UserRole userRole);

    Optional<UserRole> findById(Long id);

    void delete(Long id);
}
