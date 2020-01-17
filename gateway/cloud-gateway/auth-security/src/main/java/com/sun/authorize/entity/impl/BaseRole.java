package com.sun.authorize.entity.impl;

import cn.lecons.manage.authorization.authorize.entity.IRole;
import lombok.Data;

@Data
public class BaseRole implements IRole {
    private Long id;
    private String code;
    private String name;
}
