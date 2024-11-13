package com.pol.blog_service.dto.blog;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BlogSummaryDTO {
    private UUID id;
    private String title;
    private String publishedAt;
    private String summary;

    public BlogSummaryDTO(UUID id, String title, LocalDateTime publishedAt, String summary) {
        this.id = id;
        this.title = title;
        this.publishedAt = publishedAt.toString();
        this.summary = summary;
    }

}
