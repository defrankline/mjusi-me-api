package com.team.mjusi.controller;

import com.team.mjusi.entity.UserRole;
import com.team.mjusi.service.UserRoleService;
import com.team.mjusi.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/userRoles")
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRoleService userRoleService;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "20") int size,
                                     @RequestParam(value = "userId") Long userId) throws Exception {
        if (size <= 0) {
            List<UserRole> items = userRoleService.findAllByUserId(userId);
            ApiResponse<?> response = new ApiResponse<>("Roles", items);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            Page<UserRole> items = userRoleService.findAllByUserId(userId, pageable);
            ApiResponse<?> response = new ApiResponse<>("Roles", items);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserRole userRole) {
        return getCreateResponseEntity(userRole);
    }

    private ResponseEntity<?> validate(@RequestBody @Valid UserRole userRole) {
        if (userRole.getRole() == null) {
            ApiResponse<?> response = new ApiResponse<>("Role is required", userRole);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (userRole.getUser() == null) {
            ApiResponse<?> response = new ApiResponse<>("User is required", userRole);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    private ResponseEntity<?> getCreateResponseEntity(@RequestBody @Valid UserRole userRole) {
        ResponseEntity<?> validationResponse = validate(userRole);
        if (validationResponse != null) return validationResponse;
        try {
            UserRole newUserRole = new UserRole();
            newUserRole.setUser(userRole.getUser());
            newUserRole.setRole(userRole.getRole());
            UserRole grant = userRoleService.save(newUserRole);
            ApiResponse<?> response = new ApiResponse<>("Success", grant);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            ApiResponse<?> response = new ApiResponse<>("Success", userRole);
            return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!userRoleService.findById(id).isPresent()) {
            ApiResponse<?> response = new ApiResponse<>("User Role Assignment Doest Not Exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        userRoleService.delete(id);
        ApiResponse<?> response = new ApiResponse<>("Success", 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}