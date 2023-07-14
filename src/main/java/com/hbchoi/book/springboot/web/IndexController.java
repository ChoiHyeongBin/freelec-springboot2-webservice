package com.hbchoi.book.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index"; // 머스테치 스타터가 앞의 경로와 뒤의 파일 확장자를 자동으로 지정 (src/main/resources/templates/index.mustache)
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }
}
