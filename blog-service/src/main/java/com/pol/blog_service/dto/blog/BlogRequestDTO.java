package com.pol.blog_service.dto.blog;

import com.pol.blog_service.entity.BlogStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class BlogRequestDTO {

    @NotBlank(message = "Please provide title.")
    private String title;

    @NotBlank(message = "Please write something")
    private String content;

    @Size(max = 10, message = "You can add up to 10 tags only.")
    private Set<UUID> tagIds;

    @NotNull(message = "Please set the status of the blog post.")
    @Enumerated(EnumType.STRING)
    private BlogStatus status;
}
