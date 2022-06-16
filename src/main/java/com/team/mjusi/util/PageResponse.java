package com.team.mjusi.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PageResponse<T> implements Serializable {
    private BigInteger totalElements;
    private T content;
}
