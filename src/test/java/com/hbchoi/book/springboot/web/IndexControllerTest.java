package com.hbchoi.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IndexControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    // 메인페이지_로딩
    public void mainPageLoading() {
        // when
        String body = this.restTemplate.getForObject("/", String.class);    // getForObject: 기본 http 헤더를 사용하며 결과를 객체로 반환

        // then
        assertThat(body).contains("스프링 부트로 시작하는 웹 서비스");    // contains: 중복여부, 순서에 관계 없이 값만 일치하면 테스트가 성공
    }

}
