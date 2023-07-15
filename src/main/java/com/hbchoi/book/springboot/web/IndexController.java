package com.hbchoi.book.springboot.web;

import com.hbchoi.book.springboot.service.posts.PostsService;
import com.hbchoi.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;    // 직접 적으로 값을 참조할 수는 없지만 생성자를 통해 값을 참조할 수 있다.

    @GetMapping("/")
    public String index(Model model) {  // Model: 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장
        model.addAttribute("posts", postsService.findAllDesc());

        return "index"; // 머스테치 스타터가 앞의 경로와 뒤의 파일 확장자를 자동으로 지정 (src/main/resources/templates/index.mustache)
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
