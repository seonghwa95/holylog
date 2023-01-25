package com.holyheem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController // 데이터 기반으로 API응답 방식으로 진행할 예정 (JSON)
public class PostController {
    // SSR -> JSP, thymeleaf, mustache, freemarker
    // SPA ->
    //      vue -> vue + SSR = nuxt.js    ( 이걸로 진행할 예정 )
    //      react -> react + SSR = next.js

    // @RequestMapping(method = RequestMethod.GET, path = "/posts") -> 예전 방식
    @GetMapping("/posts")
    public String get() {
        return "Hello world";
    }
}
