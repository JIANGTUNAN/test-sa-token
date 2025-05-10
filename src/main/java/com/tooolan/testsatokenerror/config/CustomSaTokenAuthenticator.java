package com.tooolan.testsatokenerror.config;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.tooolan.testsatokenerror.entity.UserBean;

/**
 * 自定义的sa-token身份验证器
 *
 * @author tooolan
 * @since 2025年5月10日
 */
public class CustomSaTokenAuthenticator {

    public static void check() {
        // 直接获取当前用户信息 如果获取不到就没登录
        SaSession session = StpUtil.getSession();
        UserBean user = session.getModel("user", UserBean.class);
        System.out.println(user);
    }

}
