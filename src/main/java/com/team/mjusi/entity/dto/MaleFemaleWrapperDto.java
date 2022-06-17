package com.team.mjusi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaleFemaleWrapperDto implements Serializable {
    private MaleFemaleDto student;
    private MaleFemaleDto attendee;
}
