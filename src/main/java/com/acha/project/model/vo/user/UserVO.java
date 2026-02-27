package com.acha.project.model.vo.user;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户安全视图 (脱敏后的用户信息)
 */
@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String realName;

    private Integer role;

    private String phone;

    private String email;

    private Date createTime;

    private String token;

    // 注意：这里没有 password 和 isDelete 字段！
}