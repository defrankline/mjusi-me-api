package com.team.mjusi.repository;

import com.team.mjusi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndActiveTrue(String username);

    @Query("FROM User i WHERE (lower(i.name) LIKE %:query% OR lower(i.mobile) LIKE %:query% OR lower(i.email) LIKE %:query%)")
    Page<User> findAll(@Param("query") String query, Pageable pageable);
}
