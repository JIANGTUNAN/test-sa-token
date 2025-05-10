package com.tooolan.testsatokenerror.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.tooolan.testsatokenerror.entity.UserBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试用的控制器
 *
 * @author tooolan
 * @since 2025年5月10日
 */
@RestController
@RequestMapping("/api")
public class TestController {

    /**
     * 正常请求
     */
    @GetMapping("/hello")
    public String hello() {
        return "hello word";
    }

    /**
     * 登录请求
     */
    @GetMapping("/login")
    public SaTokenInfo login() {
        // 模拟数据
        UserBean userBean = new UserBean("1001", "张三");
        // 登录
        StpUtil.login(userBean.getUserName());
        // 缓存 user 对象
        StpUtil.getSession().set("user", userBean);
        //获取token
        return StpUtil.getTokenInfo();
    }

    /**
     * 注销请求
     */
    @GetMapping("/logout")
    public String logout() {
        StpUtil.logout();
        return "logout";
    }

}
