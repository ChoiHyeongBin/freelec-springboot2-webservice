package com.hbchoi.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 클래스 내 모든 필드의 Getter 메소드 자동생성
// Entity 클래스에서는 절대 Setter 메소드를 만들지 않음
@Getter
@NoArgsConstructor  // 기본 생성자 자동추가
@Entity // 테이블과 링크될 클래스임을 나타냄 (기본값으로 클래스의 카멜케이스 이름을 언더스코어(_) 네이밍으로 테이블 이름을 매칭)
public class Posts {

    @Id // 해당 테이블의 PK 필드를 나타냄
    // PK의 생성 규칙
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    // 테이블의 컬럼을 나타냄. 추가로 변경이 필요한 옵션이 있으면 선언
    @Column(length = 500, nullable = false) // not null
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;  // author: 작가

    @Builder    // 빌더 패턴 클래스 생성 (생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함)
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
