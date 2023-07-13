package com.hbchoi.book.springboot.web;

import com.hbchoi.book.springboot.domain.posts.Posts;
import com.hbchoi.book.springboot.domain.posts.PostsRepository;
import com.hbchoi.book.springboot.web.dto.PostsSaveRequestDto;
import com.hbchoi.book.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 스프링 부트의 내장 서버를 랜덤 포트로 띄움 (실제로 테스트를 위한 서블릿 컨테이너를 띄운다)
public class PostsApiControllerTest {

    @LocalServerPort    // 실제 주입되는 포트번호를 확인 가능
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;  // HTTP 요청 후 데이터를 응답 받을 수 있는 템플릿 객체이며 ResponseEntity와 함께 자주 사용

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    // 등록 테스트
    public void PostsReg() throws Exception {
        // given (주어진)
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        // when
        // ResponseEntity: 결과 데이터와 HTTP 상태 코드를 직접 제어할 수 있는 클래스
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
        assertThat(all.get(0).getAuthor()).isEqualTo("author"); // 추가
    }

    @Test
    // 수정 테스트
    public void PostsUpt() throws Exception {
        // given (주어진)
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        // HttpEntity: HTTP 요청 또는 응답에 해당하는 HttpHeader와 HttpBody를 포함하는 클래스
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        // ResponseEntity: 결과 데이터와 HTTP 상태 코드를 직접 제어할 수 있는 클래스
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

}
