package com.tooolan.testsatokenerror.controller;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.SaTokenException;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常拦截
 *
 * @author tooolan
 * @since 2025年5月10日
 */
@Slf4j
@RestControllerAdvice
public class ExceptionController {

    /**
     * 未登录异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotLoginException.class)
    public JSONObject handlerException(NotLoginException e, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        log.warn("请求({})出现校验异常", requestURL, e);
        String errMessage = switch (e.getType()) {
            case NotLoginException.NOT_TOKEN -> "未能从请求中获取有效的 token，请检查您的请求头是否包含有效的 token";
            case NotLoginException.INVALID_TOKEN -> "提供的 token 无效，请确保您提交的 token 是正确的";
            case NotLoginException.TOKEN_TIMEOUT -> "提供的 token 已经过期，请重新登录以获取新的 token";
            case NotLoginException.BE_REPLACED -> "提供的 token 已被其他设备顶下线，请重新登录";
            case NotLoginException.KICK_OUT -> "提供的 token 已被管理员踢下线，请重新登录";
            case NotLoginException.TOKEN_FREEZE -> "提供的 token 已被冻结，请联系管理员解冻";
            case NotLoginException.NO_PREFIX -> "请确保 token 前缀正确";
            default -> "当前会话未登录，请重新登录";
        };

        JSONObject result = JSONUtil.createObj();
        result.set("code", 403);
        result.set("errMessage", errMessage);
        return result;
    }

    /**
     * 统一捕获 Sa-Token 异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(SaTokenException.class)
    public JSONObject handleSaTokenException(SaTokenException e, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        log.warn("请求({})出现Sa-Token异常", requestURL, e);
        // 其他 Sa-Token 异常处理
        JSONObject result = JSONUtil.createObj();
        result.set("code", 500);
        result.set("errMessage", e.getMessage());
        return result;
    }

    /**
     * 捕捉其他所有异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public JSONObject globalException(HttpServletRequest request, Throwable ex) {
        // 记录异常到日志
        String requestURL = request.getRequestURL().toString();
        log.error("请求({})出现未知错误.具体错误为:{}", requestURL, ex.getMessage(), ex);
        // 其他 Sa-Token 异常处理
        JSONObject result = JSONUtil.createObj();
        result.set("code", 500);
        result.set("errMessage", "请求出现未知错误.请联系运维人员排查错误");
        return result;
    }

}
