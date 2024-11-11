package com.pol.blog_service.dto.blog;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BlogSummaryDTO {
    private UUID id;
    private String title;
    private String publishedAt;
    private String summary;
}
