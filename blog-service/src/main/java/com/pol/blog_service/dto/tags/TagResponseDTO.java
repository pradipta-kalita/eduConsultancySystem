package com.pol.blog_service.dto.tags;

import com.pol.blog_service.dto.blog.BlogSummaryDTO;
import com.pol.blog_service.entity.Blog;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class TagResponseDTO {
    private UUID id;
    private String tagName;
    private Set<BlogSummaryDTO> blogs= new HashSet<>();
}
