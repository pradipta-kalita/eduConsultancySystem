package com.pol.blog_service.dto.tags;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class TagRequestDTO {

    @NotBlank(message = "Please provide a tag name")
    private String tagName;

}
