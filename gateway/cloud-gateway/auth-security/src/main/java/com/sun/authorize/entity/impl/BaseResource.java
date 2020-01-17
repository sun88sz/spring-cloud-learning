package com.sun.authorize.entity.impl;

import cn.lecons.manage.authorization.authorize.entity.IResource;
import lombok.Data;

@Data
public class BaseResource implements IResource {
    private Long id;
    private String code;
    private String type;


}
