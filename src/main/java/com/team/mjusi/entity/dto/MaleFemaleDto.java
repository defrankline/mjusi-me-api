package com.team.mjusi.entity.dto;

import com.team.mjusi.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaleFemaleDto implements Serializable {
    private BigInteger male;
    private BigInteger female;
}
