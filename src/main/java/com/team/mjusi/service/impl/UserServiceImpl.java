package com.team.mjusi.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.mjusi.entity.Role;
import com.team.mjusi.entity.User;
import com.team.mjusi.entity.dto.UserDto;
import com.team.mjusi.repository.UserRepository;
import com.team.mjusi.service.RoleService;
import com.team.mjusi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> row = repository.findByUsernameAndActiveTrue(s);
        if (row.isPresent()) {
            User user = row.get();
            Set<Role> roles = user.getRoles();
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (roles.size() > 0) {
                roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
            }
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(), user.getPassword(), authorities
            );
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }

    @Override
    public Optional<User> findFirstByUsername(String username) {
        return repository.findByUsernameAndActiveTrue(username);
    }

    @Override
    public User getUser(String username) {
        Optional<User> row = findFirstByUsername(username);
        return row.orElse(null);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public User create(UserDto userDto) throws ParseException {
        User user = convertToEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return store(userDto, user);
    }

    private User store(UserDto userDto, User user) {
        Set<Role> roles = new HashSet<>();
        if (userDto.getRoles().size() > 0) {
            for (Role role : userDto.getRoles()) {
                Optional<Role> rw = roleService.findById(role.getId());
                rw.ifPresent(roles::add);
            }
        }
        user.setRoles(roles);
        return repository.save(user);
    }

    @Override
    public User update(UserDto userDto) throws ParseException {
        User user = convertToEntity(userDto);
        return store(userDto, user);
    }


    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User convertToEntity(UserDto userDto) throws ParseException {
        return modelMapper.map(userDto, User.class);
    }

    @Override
    public User getCurrentUser(Principal principal) {
        Optional<User> row = repository.findByUsernameAndActiveTrue(principal.getName());
        return row.orElse(null);
    }

    @Override
    public void tokenResponse(HttpServletResponse response, String refreshToken, String accessToken) throws IOException {
        Map<String, Object> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        tokens.put("tokeExpire", System.currentTimeMillis() + 1440 * 60 * 1000);

        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
