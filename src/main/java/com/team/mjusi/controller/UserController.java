package com.team.mjusi.controller;


import com.team.mjusi.entity.User;
import com.team.mjusi.entity.dto.UserDto;
import com.team.mjusi.service.UserService;
import com.team.mjusi.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDto userDto) throws ParseException {
        String password = userDto.getPassword();
        String username = userDto.getUsername();
        if (userDto.getPassword() == null) {
            password = "Secret1234";
        }

        userDto.setPassword(password);
        userDto.setUsername(username);

        ResponseEntity<?> validate = validate(userDto);
        if (validate != null) return validate;

        User user = userService.create(userDto);
        ApiResponse<?> response = new ApiResponse<>("User Created Successfully", user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<?> getAll(Principal principal,
                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "0") int size,
                                    @RequestParam(value = "sortBy", defaultValue = "id") String sortBy) {
        if(size <= 0){
            List<User> items = userService.findAll();
            ApiResponse<?> response = new ApiResponse<>("Users", items);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else{
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            Page<User> items = userService.findAll(pageable);
            ApiResponse<?> response = new ApiResponse<>("Role", items);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id) {
        Optional<User> row = userService.findById(id);
        if (row.isPresent()) {
            User user = row.get();
            ApiResponse<?> response = new ApiResponse<>("User", user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<?> response = new ApiResponse<>("User not found", id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody UserDto userDto) throws ParseException {
        Optional<User> row = userService.findById(id);
        if (row.isPresent()) {
            userDto.setUsername(userDto.getUsername() != null ? userDto.getUsername() : row.get().getUsername());
            userDto.setPassword(row.get().getPassword());
            ResponseEntity<?> validate = validate(userDto);
            if (validate != null) return validate;
            User user = userService.update(userDto);
            ApiResponse<?> response = new ApiResponse<>("User Updated Successfully", user);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } else {
            ApiResponse<?> response = new ApiResponse<>("User Not Found", userDto);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<User> row = userService.findById(id);
        if (row.isPresent()) {
            userService.delete(id);
            ApiResponse<?> response = new ApiResponse<>("No Content", "User Deleted Successfully");
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } else {
            ApiResponse<?> response = new ApiResponse<>("User Not Found", id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    private ResponseEntity<?> validate(@RequestBody UserDto user) {
        if (user.getName().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>("First Name is required", user);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (user.getUsername().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>("Surname is required", user);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @GetMapping("/current")
    public ResponseEntity<?> current(Principal principal) {
        User user = userService.getCurrentUser(principal);
        ApiResponse<?> response = new ApiResponse<>("Current", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
