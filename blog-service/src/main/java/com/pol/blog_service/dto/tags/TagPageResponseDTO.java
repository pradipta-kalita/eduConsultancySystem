package com.pol.blog_service.dto.tags;

import com.pol.blog_service.dto.blog.BlogSummaryDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class TagPageResponseDTO {
    private List<BlogSummaryDTO> blogs ;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private boolean hasNext;
    private boolean hasPrevious;
}
