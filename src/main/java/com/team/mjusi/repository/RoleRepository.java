package com.team.mjusi.repository;

import com.team.mjusi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findFirstByName(String name);

    @Query("FROM Role f where f.id not in (:exceptIds)")
    List<Role> findAllExcept(@Param("exceptIds") List<Long> exceptIds);

    @Query("FROM Role f where f.id in (:ids)")
    List<Role> findAllIds(@Param("ids") List<Long> ids);
}
