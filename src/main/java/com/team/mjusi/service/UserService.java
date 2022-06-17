package com.team.mjusi.service;

import com.team.mjusi.entity.User;
import com.team.mjusi.entity.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface UserService {
    User getUser(String username);

    List<User> findAll();

    Page<User> findAll(Pageable pageable);
    List<User> findAll(String query);

    User save(User user);

    User create(UserDto userDto) throws ParseException;

    User update(UserDto userDto) throws ParseException;

    void delete(Long id);

    Optional<User> findById(Long id);

    UserDto convertToDto(User user);

    User convertToEntity(UserDto userDto) throws ParseException;

    User getCurrentUser(Principal principal);

    Optional<User> findFirstByUsername(String username);
}
