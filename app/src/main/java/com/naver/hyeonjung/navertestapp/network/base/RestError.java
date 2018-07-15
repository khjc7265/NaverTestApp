package com.naver.hyeonjung.navertestapp.network.base;

import lombok.Data;

@Data
public class RestError {
    private String error;
    private String error_description;
}
