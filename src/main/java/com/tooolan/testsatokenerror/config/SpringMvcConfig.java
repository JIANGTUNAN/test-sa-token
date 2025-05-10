package com.tooolan.testsatokenerror.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * SpringMvc配置类
 *
 * @author tooolan
 * @since 2025年5月10日
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器 定义详细认证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
            // 指定一条 match 规则
            SaRouter
                    // 拦截的 path 列表
                    .match("/**")
                    // 排除掉的 path 列表
                    .notMatch("/api/login")
                    // 要执行的校验动作
                    .check(CustomSaTokenAuthenticator::check);
        })).addPathPatterns("/**")

        // TODO 注释掉这个做测试
//        .excludePathPatterns("/error")

        ;

    }

}
