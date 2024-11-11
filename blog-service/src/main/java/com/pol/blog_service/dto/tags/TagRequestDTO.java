package com.pol.blog_service.dto.tags;

import com.pol.blog_service.entity.Blog;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Data
@Builder
public class TagRequestDTO {

    @NotBlank(message = "Please provide a tag name")
    private String tagName;

    private Set<UUID> blogIds = new HashSet<>();
}
