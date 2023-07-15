package com.hbchoi.book.springboot.service.posts;

import com.hbchoi.book.springboot.domain.posts.Posts;
import com.hbchoi.book.springboot.domain.posts.PostsRepository;
import com.hbchoi.book.springboot.web.dto.PostsListResponseDto;
import com.hbchoi.book.springboot.web.dto.PostsResponseDto;
import com.hbchoi.book.springboot.web.dto.PostsSaveRequestDto;
import com.hbchoi.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    // 구글: 읽기 전용 메서드. 영속성 컨텍스트에 관리를 받지않게 되고, 변경감지 작업을 수행하지 않아 성능상의 이점이 있음
    // 책: 트랜잭션 범위는 유지하고 조회 속도는 개선
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        // collect(Collectors.toList()): Stream을 List로 변환
        return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        // 존재하는 Posts인지 확인을 위해 엔티티 조회
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postsRepository.delete(posts);  // JpaRepository에서 delete() 지원
    }

}
