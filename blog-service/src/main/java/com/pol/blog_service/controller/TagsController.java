package com.pol.blog_service.controller;


import com.pol.blog_service.dto.tags.TagPageResponseDTO;
import com.pol.blog_service.dto.tags.TagRequestDTO;
import com.pol.blog_service.dto.tags.TagResponseDTO;
import com.pol.blog_service.dto.tags.TagSummaryDTO;
import com.pol.blog_service.service.tags.TagsService;
import com.pol.blog_service.service.tags.TagsServiceImpl;
import com.pol.blog_service.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tags")
public class TagsController {

    private final TagsService tagsService;

    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @PostMapping
    public ResponseEntity<TagResponseDTO> createTag(@RequestBody @Valid TagRequestDTO tagRequestDTO){
        return ResponseEntity.ok(tagsService.createTag(tagRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<TagSummaryDTO>> getAllTags(){
        return ResponseEntity.ok(tagsService.getAllTags());
    }

    @GetMapping("/{id}/blogs")
    public ResponseEntity<TagPageResponseDTO> getAssociatedBlogsByTagId(
            @PathVariable UUID id,
            @RequestParam(defaultValue = AppConstants.PAGE,required = false) int page,
            @RequestParam(defaultValue = AppConstants.SIZE,required = false) int size,
            @RequestParam(defaultValue = AppConstants.SORT_BY_BLOG_PUBLISHED_AT,required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.ORDER,required = false) String order
    ){
        return ResponseEntity.ok(tagsService.getBlogsByTagId(id,page,size,sortBy,order));
    }

    @DeleteMapping("/{id}")
    public void deleteTagById(@PathVariable UUID id){
        tagsService.deleteTagById(id);
    }

}
