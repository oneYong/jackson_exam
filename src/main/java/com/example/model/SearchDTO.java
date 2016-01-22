package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by kimwonyong on 2016. 1. 22..
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {
    private String userId;
    private String userName;
}
