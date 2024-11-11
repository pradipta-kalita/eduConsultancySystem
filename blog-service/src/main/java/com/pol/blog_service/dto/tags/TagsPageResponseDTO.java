package com.pol.blog_service.dto.tags;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TagsPageResponseDTO {
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private boolean hasNext;
    private boolean hasPrevious;
}
