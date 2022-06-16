package com.team.mjusi.entity.dto;

import com.team.mjusi.entity.Gender;
import com.team.mjusi.entity.MaritalStatus;
import com.team.mjusi.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private Long id;

    private String username;

    private String name;

    private String email;

    private String mobile;

    private Gender gender;

    private MaritalStatus maritalStatus;

    private String password;

    private LocalDate dob;

    private Set<Role> roles;
}
