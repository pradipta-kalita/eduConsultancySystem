package com.pol.blog_service.repository;


import com.pol.blog_service.dto.blog.BlogSummaryDTO;
import com.pol.blog_service.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BlogRepository extends JpaRepository<Blog, UUID> {

    @Query("SELECT new com.pol.blog_service.dto.blog.BlogSummaryDTO(b.id, b.title, b.publishedAt, b.summary) " +
            "FROM Blog b JOIN b.tags t WHERE t.id = :tagId")
    Page<BlogSummaryDTO> findAllBlogSummariesByTagId(@Param("tagId") UUID tagId, Pageable pageable);

    @Query("SELECT new com.pol.blog_service.dto.blog.BlogSummaryDTO(b.id, b.title, b.publishedAt, b.summary) " +
            "FROM Blog b " +
            "WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(b.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<BlogSummaryDTO> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT new com.pol.blog_service.dto.blog.BlogSummaryDTO(b.id, b.title, b.publishedAt, b.summary) " +
            "FROM Blog b")
    Page<BlogSummaryDTO> findAllBlogSummary(Pageable pageable);
}

