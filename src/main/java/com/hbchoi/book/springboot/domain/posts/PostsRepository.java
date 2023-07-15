package com.hbchoi.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// MyBatis 등에서 Dao라고 불리는 DB Layer 접근자
// CRUD 메소드가 자동으로 생성

public interface PostsRepository extends JpaRepository<Posts, Long> {   // JpaRepository<Entity 클래스, PK 타입>

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")  // SQL과 유사한 JPQL(Java Persistence Query Language)이라는 객체지향 쿼리 언어를 통해 복잡한 쿼리 처리를 지원
    List<Posts> findAllDesc();

}
