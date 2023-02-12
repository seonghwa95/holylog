package com.holyheem.controller;

import com.holyheem.request.PostCreate;
import com.holyheem.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
            postService.write(request);
    }
}
