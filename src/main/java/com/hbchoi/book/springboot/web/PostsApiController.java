package com.hbchoi.book.springboot.web;

import com.hbchoi.book.springboot.service.posts.PostsService;
import com.hbchoi.book.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    // RequestBody: 받아온 Json 포맷의 데이터를 java 객체로 자동으로 역직렬화, url상에서 데이터를 전달하는 경우(form 태그 등)에는 @RequestParam을 사용
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

}
