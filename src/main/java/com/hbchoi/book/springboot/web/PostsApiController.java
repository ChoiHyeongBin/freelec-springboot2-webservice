package com.hbchoi.book.springboot.web;

import com.hbchoi.book.springboot.service.posts.PostsService;
import com.hbchoi.book.springboot.web.dto.PostsResponseDto;
import com.hbchoi.book.springboot.web.dto.PostsSaveRequestDto;
import com.hbchoi.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    // 등록
    @PostMapping("/api/v1/posts")
    // RequestBody: 받아온 Json 포맷의 데이터를 java 객체로 자동으로 역직렬화, url상에서 데이터를 전달하는 경우(form 태그 등)에는 @RequestParam을 사용
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    // 수정
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) { // PathVariable: REST API 호출 시, URI값에 가변형 변수를 전달해서 처리하는 방식
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {   // URI 가변형 변수값과 파라미터 변수명이 일치해야 됨
        postsService.delete(id);
        return id;
    }

}
