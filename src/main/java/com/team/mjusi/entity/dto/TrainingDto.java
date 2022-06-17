package com.team.mjusi.entity.dto;

import com.team.mjusi.entity.Gender;
import com.team.mjusi.entity.MaritalStatus;
import com.team.mjusi.entity.Role;
import com.team.mjusi.entity.User;
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
public class TrainingDto implements Serializable {
    private Long id;
    private String name;
    private LocalDate date;
    private Set<User> users;
}
