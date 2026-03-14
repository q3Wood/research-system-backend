package com.acha.project.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpdateMyRequestDTO implements Serializable {

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;
}
