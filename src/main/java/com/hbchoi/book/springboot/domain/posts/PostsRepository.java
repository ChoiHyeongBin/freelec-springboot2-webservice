package com.hbchoi.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

// MyBatis 등에서 Dao라고 불리는 DB Layer 접근자
// CRUD 메소드가 자동으로 생성

public interface PostsRepository extends JpaRepository<Posts, Long> {   // JpaRepository<Entity 클래스, PK 타입>

}
