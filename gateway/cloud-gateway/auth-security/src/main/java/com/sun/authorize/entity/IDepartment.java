package com.sun.authorize.entity;

import cn.lecons.manage.authorization.authorize.entity.impl.DepartmentSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = DepartmentSerializer.class)
public interface IDepartment {

    /**
     * @return 部门ID
     */
    Long getId();

    /**
     * @return 部门名
     */
    String getName();
}
