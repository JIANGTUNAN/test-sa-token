package com.tooolan.testsatokenerror.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息实体类
 *
 * @author tooolan
 * @since 2025年5月10日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBean {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

}
