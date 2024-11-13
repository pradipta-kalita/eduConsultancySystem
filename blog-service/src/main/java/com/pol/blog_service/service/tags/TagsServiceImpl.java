package com.pol.blog_service.service.tags;

import com.pol.blog_service.dto.blog.BlogSummaryDTO;
import com.pol.blog_service.dto.tags.TagPageResponseDTO;
import com.pol.blog_service.dto.tags.TagRequestDTO;
import com.pol.blog_service.dto.tags.TagResponseDTO;
import com.pol.blog_service.dto.tags.TagSummaryDTO;
import com.pol.blog_service.entity.Blog;
import com.pol.blog_service.entity.Tags;
import com.pol.blog_service.exception.customExceptions.EntityNotFound;
import com.pol.blog_service.mapper.TagsMapper;
import com.pol.blog_service.repository.BlogRepository;
import com.pol.blog_service.repository.TagsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class TagsServiceImpl implements TagsService {
    private final TagsRepository tagsRepository;
    private final BlogRepository blogRepository;

    public TagsServiceImpl(TagsRepository tagsRepository, BlogRepository blogRepository) {
        this.tagsRepository = tagsRepository;
        this.blogRepository = blogRepository;
    }

    @Override
    public TagResponseDTO createTag(TagRequestDTO tagRequestDTO) {
        return TagsMapper.toResponseDTO(tagsRepository.save(TagsMapper.toEntity(tagRequestDTO)));
    }

    @Override
    public TagSummaryDTO updateTagById(UUID id, TagRequestDTO tagRequestDTO) {
        if(!tagsRepository.existsById(id)){
            throw new EntityNotFound("Tag not found with id: "+id);
        };
        Tags tag= tagsRepository.findById(id).orElseThrow(()-> new EntityNotFound("Tag not found with id: "+id)); // make a custom query to not load all the blogs
        tag.setTagName(tagRequestDTO.getTagName());
        return TagsMapper.toSummaryDTO(tagsRepository.save(tag));
    }

    @Override
    public TagPageResponseDTO getBlogsByTagId(UUID id, int page, int size, String sortBy, String order) {
        if(!tagsRepository.existsById(id)){
            throw new EntityNotFound("Tag not found with id: "+id);
        };
        String[] sortFields = sortBy.split(",");
        Sort sort = Sort.by(order.equalsIgnoreCase("asc")?Sort.Order.asc(sortFields[0]):Sort.Order.desc(sortFields[0]));
        for(int i=1;i<sortFields.length;i++){
            sort= Sort.by(order.equalsIgnoreCase("asc")?Sort.Order.asc(sortFields[i]):Sort.Order.desc(sortFields[i]));
        }
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<BlogSummaryDTO> blogSummaryDTOPage = blogRepository.findAllBlogSummariesByTagId(id,pageable);
        return TagPageResponseDTO.builder()
                .blogs(blogSummaryDTOPage.getContent())
                .currentPage(blogSummaryDTOPage.getNumber())
                .totalElements(blogSummaryDTOPage.getTotalElements())
                .totalPages(blogSummaryDTOPage.getTotalPages())
                .pageSize(blogSummaryDTOPage.getSize())
                .hasNext(blogSummaryDTOPage.hasNext())
                .hasPrevious(blogSummaryDTOPage.hasPrevious())
                .build();
    }

    @Override
    public void deleteTagById(UUID id) {
        if(!tagsRepository.existsById(id)){
            throw new EntityNotFound("Tag not found with id: "+id);
        }
        tagsRepository.deleteTagAssociations(id);
        tagsRepository.deleteById(id);
    }

    @Override
    public List<TagSummaryDTO> getAllTags() {
        return tagsRepository.getAllTagSummary();
    }
}
