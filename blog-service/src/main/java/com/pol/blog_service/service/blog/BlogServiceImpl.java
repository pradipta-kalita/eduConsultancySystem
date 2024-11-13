package com.pol.blog_service.service.blog;

import com.pol.blog_service.dto.blog.BlogPageResponseDTO;
import com.pol.blog_service.dto.blog.BlogRequestDTO;
import com.pol.blog_service.dto.blog.BlogResponseDTO;
import com.pol.blog_service.dto.blog.BlogSummaryDTO;
import com.pol.blog_service.entity.Blog;
import com.pol.blog_service.entity.BlogStatus;
import com.pol.blog_service.exception.customExceptions.EntityNotFound;
import com.pol.blog_service.mapper.BlogMapper;
import com.pol.blog_service.repository.BlogRepository;
import com.pol.blog_service.repository.TagsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;


@Service
public class BlogServiceImpl implements BlogService{

    private final BlogRepository blogRepository;
    private final TagsRepository tagsRepository;

    public BlogServiceImpl(BlogRepository blogRepository, TagsRepository tagsRepository) {
        this.blogRepository = blogRepository;
        this.tagsRepository = tagsRepository;
    }

    @Override
    public BlogResponseDTO createBlog(BlogRequestDTO blogRequestDTO){
        Blog blog = BlogMapper.toEntity(blogRequestDTO);
        blog.setTags(new HashSet<>(tagsRepository.findAllById(blogRequestDTO.getTagIds())));
        blog.setPublishedAt(blogRequestDTO.getStatus()== BlogStatus.PUBLISHED? LocalDateTime.now():null);
        return BlogMapper.toResponseDTO(blogRepository.save(blog));
    }

    @Override
    public BlogResponseDTO updateBlog(BlogRequestDTO blogRequestDTO,UUID id){
        Blog blog = blogRepository.findById(id).orElseThrow(()->new EntityNotFound("Blog not found with id: "+id));
        blog.setTitle(blogRequestDTO.getTitle());
        blog.setContent(blogRequestDTO.getContent());
        blog.setStatus(blogRequestDTO.getStatus());
        blog.setTags(new HashSet<>(tagsRepository.findAllById(blogRequestDTO.getTagIds())));
        blog.setPublishedAt(blogRequestDTO.getStatus()== BlogStatus.PUBLISHED? LocalDateTime.now():null);
        return BlogMapper.toResponseDTO(blogRepository.save(blog));
    }

    @Override
    public BlogResponseDTO getBlogById(UUID id){
        Blog blog = blogRepository.findById(id).orElseThrow(()->new EntityNotFound("Blog not found with id: "+id));
        return BlogMapper.toResponseDTO(blog);
    }

    @Override
    public void deleteBlogById(UUID id){
        Blog blog = blogRepository.findById(id).orElseThrow(()->new EntityNotFound("Blog not found with id: "+id));
        blogRepository.deleteById(id);
    }

    @Override
    public BlogPageResponseDTO getAllBlogs(int page, int size, String sortBy, String order) {
        String[] sortFields = sortBy.split(",");
        Sort sort = Sort.by(order.equalsIgnoreCase("asc")?Sort.Order.asc(sortFields[0]):Sort.Order.desc(sortFields[0]));
        for(int i=1;i<sortFields.length;i++){
            sort= Sort.by(order.equalsIgnoreCase("asc")?Sort.Order.asc(sortFields[i]):Sort.Order.desc(sortFields[i]));
        }
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<BlogSummaryDTO> blogs = blogRepository.findAllBlogSummary(pageable);
        return BlogPageResponseDTO.builder()
                .blogs(blogs.getContent())
                .currentPage(blogs.getNumber())
                .totalPages(blogs.getTotalPages())
                .totalElements(blogs.getTotalElements())
                .pageSize(blogs.getSize())
                .hasNext(blogs.hasNext())
                .hasPrevious(blogs.hasPrevious())
                .build();
    }

    @Override
    public BlogPageResponseDTO searchBlogsByKeyword(String keyword, int page, int size, String sortBy, String order) {
        String[] sortFields = sortBy.split(",");
        Sort sort = Sort.by(order.equalsIgnoreCase("asc")?Sort.Order.asc(sortFields[0]):Sort.Order.desc(sortFields[0]));
        for(int i=1;i<sortFields.length;i++){
            sort= Sort.by(order.equalsIgnoreCase("asc")?Sort.Order.asc(sortFields[i]):Sort.Order.desc(sortFields[i]));
        }
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<BlogSummaryDTO> blogSummaryDTOs = blogRepository.searchByKeyword(keyword,pageable);
        return BlogPageResponseDTO.builder()
                .blogs(blogSummaryDTOs.getContent())
                .currentPage(blogSummaryDTOs.getNumber())
                .totalPages(blogSummaryDTOs.getTotalPages())
                .totalElements(blogSummaryDTOs.getTotalElements())
                .pageSize(blogSummaryDTOs.getSize())
                .hasNext(blogSummaryDTOs.hasNext())
                .hasPrevious(blogSummaryDTOs.hasPrevious())
                .build();
    }


}
