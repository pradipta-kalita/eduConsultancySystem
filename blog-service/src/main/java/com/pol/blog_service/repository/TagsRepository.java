package com.pol.blog_service.repository;

import com.pol.blog_service.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TagsRepository extends JpaRepository<Tags, UUID> {
}