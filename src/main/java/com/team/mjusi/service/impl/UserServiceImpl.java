package com.team.mjusi.service.impl;


import com.team.mjusi.entity.Role;
import com.team.mjusi.entity.User;
import com.team.mjusi.entity.dto.UserDto;
import com.team.mjusi.repository.UserRepository;
import com.team.mjusi.service.RoleService;
import com.team.mjusi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;

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
    public List<User> findAll(String query) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        Page<User> page = repository.findAll(query, pageable);
        return page.getContent();
    }
}
