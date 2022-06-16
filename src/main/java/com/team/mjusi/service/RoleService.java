package com.team.mjusi.service;

import com.team.mjusi.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface RoleService {
    List<Role> findAll();

    Page<Role> findAll(Pageable pageable);

    Role save(Role role);

    void delete(Long id);

    Optional<Role> findById(Long id);

    Optional<Role> findByName(String name);

    List<Role> findAllExcept(List<Long> exceptIds);

    List<Role> findAllIds(List<Long> ids);
}
