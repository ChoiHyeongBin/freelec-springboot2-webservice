package com.hbchoi.book.springboot.service.posts;

import com.hbchoi.book.springboot.domain.posts.Posts;
import com.hbchoi.book.springboot.domain.posts.PostsRepository;
import com.hbchoi.book.springboot.web.dto.PostsResponseDto;
import com.hbchoi.book.springboot.web.dto.PostsSaveRequestDto;
import com.hbchoi.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service    // 해당 클래스를 루트 컨테이너에 빈(Bean) 객체로 생성
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional  // 메서드가 포함하고 있는 작업 중에 하나라도 실패할 경우 전체 작업을 취소
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        // orElseThrow: 에러 생성
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        // findById: 한건에 대한 데이터. 실제 DB 에 요청해서 엔티티를 가져옴
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

}
