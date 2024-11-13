package com.pol.blog_service.repository;

import com.pol.blog_service.dto.tags.TagSummaryDTO;
import com.pol.blog_service.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagsRepository extends JpaRepository<Tags, UUID> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM blog_tags WHERE tag_id = :tagId", nativeQuery = true)
    void deleteTagAssociations(@Param("tagId") UUID tagId);

    @Query("SELECT new com.pol.blog_service.dto.tags.TagSummaryDTO(t.id, t.tagName) " +
    "FROM Tags t")
    List<TagSummaryDTO> getAllTagSummary();
}
