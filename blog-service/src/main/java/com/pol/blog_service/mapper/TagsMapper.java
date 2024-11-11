package com.pol.blog_service.mapper;

import com.pol.blog_service.dto.tags.TagRequestDTO;
import com.pol.blog_service.dto.tags.TagResponseDTO;
import com.pol.blog_service.dto.tags.TagSummaryDTO;
import com.pol.blog_service.entity.Tags;

import java.util.stream.Collectors;

public class TagsMapper {
    public static Tags toEntity(TagRequestDTO tagRequestDTO){
        return Tags.builder()
                .tagName(tagRequestDTO.getTagName())
                .build();
    }

    public static TagResponseDTO toResponseDTO(Tags tag){
        return TagResponseDTO.builder()
                .id(tag.getId())
                .tagName(tag.getTagName())
                .blogs(tag.getBlogs().stream().map(BlogMapper::toSummaryDTO).collect(Collectors.toSet()))
                .build();
    }

    public static TagSummaryDTO toSummaryDTO(Tags tag){
        return TagSummaryDTO.builder()
                .id(tag.getId())
                .tagName(tag.getTagName())
                .build();
    }
}
