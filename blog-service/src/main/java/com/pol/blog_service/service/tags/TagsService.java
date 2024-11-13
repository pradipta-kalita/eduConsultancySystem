package com.pol.blog_service.service.tags;

import com.pol.blog_service.dto.tags.TagPageResponseDTO;
import com.pol.blog_service.dto.tags.TagRequestDTO;
import com.pol.blog_service.dto.tags.TagResponseDTO;
import com.pol.blog_service.dto.tags.TagSummaryDTO;

import java.util.List;
import java.util.UUID;

public interface TagsService {
    TagResponseDTO createTag(TagRequestDTO tagRequestDTO);
    TagSummaryDTO updateTagById(UUID id , TagRequestDTO tagRequestDTO);
    TagPageResponseDTO getBlogsByTagId(UUID id, int page, int size, String sortBy, String order);
    void deleteTagById(UUID id);
    List<TagSummaryDTO> getAllTags();
}
