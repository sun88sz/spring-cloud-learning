package com.sun.authorize.entity.impl;

import com.sun.authorize.entity.IDepartment;
import lombok.Data;

@Data
public class BaseDepartment implements IDepartment {
    private Long id;
    private String name;
}
