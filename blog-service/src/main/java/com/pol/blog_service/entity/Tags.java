package com.pol.blog_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tags")
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "tag_name",nullable = false,unique = true)
    private String tagName;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<Blog> blogs;
}
