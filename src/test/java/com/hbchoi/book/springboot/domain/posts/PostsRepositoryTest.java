package com.hbchoi.book.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)    // 스프링 부트 테스트와 JUnit 사이에 연결자 역할
@SpringBootTest // H2 데이터베이스를 자동으로 실행
public class PostsRepositoryTest {

    @Autowired  // 스프링이 관리하는 빈을 주입 받음
    PostsRepository postsRepository;

    @After  // Junit에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    // 게시글저장_불러오기
    public void postSaveLoad() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        // 테이블 posts에 insert/update 쿼리를 실행 (id 값이 있다면 update, 없다면 insert 실행)
        postsRepository.save(Posts.builder()
                                .title(title)
                                .content(content)
                                .author("hbchoi@gmail.com")
                                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();  // 테이블 posts에 있는 모든 데이터를 조회

        // then
        Posts posts = postsList.get(0);
        // assertThat: 검증하고 싶은 대상을 메소드 인자로 받음
        // isEqualTo: assertThat 값과 비교하여 같을 때만 성공
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    // BaseTimeEntity_등록
    public void BaseTimeEntityReg() {
        // given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0, 0);  // of: 파라미터로 주어진 시간 정보를 저장
        postsRepository.save(Posts.builder()    // save: 새 엔터티를 추가, 업데이트를 위한 용도로도 사용
                .title("title")
                .content("content")
                .author("author")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>>>>> createDate=" + posts.getCreatedDate() + ", modifiedDate=" + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);    // now보다 posts의 createdDate가 미래이면 true, 그렇지 않으면 false를 리턴
        assertThat(posts.getModifiedDate()).isAfter(now);
    }

}
