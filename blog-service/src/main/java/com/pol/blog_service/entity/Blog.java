package com.pol.blog_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "blogs")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String title;

    @Column(columnDefinition = "MEDIUMTEXT",nullable = false)
    private String content;

    @Column(length = 255)
    private String summary;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "blog_tags",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tags> tags;

    @Column(updatable = false)
    private LocalDateTime publishedAt;

    @Column(nullable = false)
    private BlogStatus status;

    @JsonIgnore
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();
        if (this.summary == null || this.summary.isEmpty()) {
            this.summary = generateSummary();
        }
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt=LocalDateTime.now();
        if (this.summary == null || this.summary.isEmpty()) {
            this.summary = generateSummary();
        }
    }

    protected String generateSummary(){
        int length=250;
        return this.content!=null && this.content.length()>length?
                this.content.substring(0,length)+"...":
                this.content;
    }
}
