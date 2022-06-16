package com.team.mjusi.repository;

import com.team.mjusi.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> findAllByUserId(Long userId);

    Optional<UserRole> findByUserIdAndRoleId(Long userId, Long roleId);

    int countAllByUserIdAndRoleId(Long userId, Long roleId);

    @Query("SELECT ur.role.name FROM UserRole ur WHERE ur.user.id =:userId")
    List<String> userRoles(@Param("userId") Long userId);

    Page<UserRole> findAllByUserId(@Param("userId") Long userId, Pageable pageable);
}
