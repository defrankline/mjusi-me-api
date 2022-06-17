package com.team.mjusi.entity.dto;

import com.team.mjusi.entity.Training;
import com.team.mjusi.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTrainingCreateDto implements Serializable {
    private User user;
    private List<Training> trainings;
}
