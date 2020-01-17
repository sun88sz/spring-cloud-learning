package com.sun.controller;

import com.sun.authorize.entity.impl.Response;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户认证
 *
 * @author SunChenjie
 * @version 1.0
 */
@RestController
@RequestMapping
public class AuthenticationController {

    @GetMapping("/me")
    public Response<?> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new Response<>(authentication);
    }
}
