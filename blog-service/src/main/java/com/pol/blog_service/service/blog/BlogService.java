package com.pol.blog_service.service.blog;

import com.pol.blog_service.dto.blog.BlogPageResponseDTO;
import com.pol.blog_service.dto.blog.BlogRequestDTO;
import com.pol.blog_service.dto.blog.BlogResponseDTO;

import java.util.UUID;

public interface BlogService {
    BlogResponseDTO createBlog(BlogRequestDTO blogRequestDTO);
    BlogResponseDTO updateBlog(BlogRequestDTO blogRequestDTO, UUID id);
    BlogResponseDTO getBlogById(UUID id);
    void deleteBlogById(UUID id);
    BlogPageResponseDTO getAllBlogs(int page, int size, String sortBy, String order);
}
