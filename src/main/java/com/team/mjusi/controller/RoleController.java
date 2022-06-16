package com.team.mjusi.controller;

import com.team.mjusi.entity.Role;
import com.team.mjusi.service.RoleService;
import com.team.mjusi.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Role roleDto) {

        ResponseEntity<?> validate = validate(roleDto);
        if (validate != null) return validate;

        Role role = roleService.save(roleDto);
        ApiResponse<?> response = new ApiResponse<>("Role Created Successfully", role);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private ResponseEntity<?> validate(@RequestBody Role roleDto) {
        if (roleDto.getName().isEmpty()) {
            ApiResponse<?> response = new ApiResponse<>("Name is required", roleDto);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "0") int size,
                                    @RequestParam(value = "sortBy", defaultValue = "id") String sortBy) throws Exception {
        if (size == 0) {
            List<Role> roles = roleService.findAll();
            ApiResponse<?> response = new ApiResponse<>("Roles", roles);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
            Page<Role> roles = roleService.findAll(pageable);
            ApiResponse<?> response = new ApiResponse<>("Roles", roles);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/getAllExcept")
    public ResponseEntity<?> getAllExcept(@RequestParam(value = "exceptIds") String exceptIds) {
        List<Long> selectedProducts = Stream.of(exceptIds.split(",")).map(String::trim).map(Long::parseLong).collect(Collectors.toList());
        List<Role> items = roleService.findAllExcept(selectedProducts);
        ApiResponse<?> response = new ApiResponse<>("list", items);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id) {
        Optional<Role> row = roleService.findById(id);
        if (row.isPresent()) {
            Role role = row.get();
            ApiResponse<?> response = new ApiResponse<>("Role", role);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<?> response = new ApiResponse<>("Role not found", id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Role roleDto) {
        Optional<Role> row = roleService.findById(id);
        if (row.isPresent()) {
            Role role = roleService.save(roleDto);
            ApiResponse<?> response = new ApiResponse<>("Role Updated Successfully", role);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } else {
            ApiResponse<?> response = new ApiResponse<>("Role Not Found", roleDto);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<Role> row = roleService.findById(id);
        if (row.isPresent()) {
            roleService.delete(id);
            ApiResponse<?> response = new ApiResponse<>("No Content", "Role Deleted Successfully");
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } else {
            ApiResponse<?> response = new ApiResponse<>("Role Not Found", id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
