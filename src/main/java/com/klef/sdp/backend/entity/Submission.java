package com.klef.sdp.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="submission_table")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length=255, nullable=false)
    private String fileName;

    @Column(length=500, nullable=false)
    private String filePath;

    @Column(length=100)
    private String contentType;

    private Long fileSize;

    @Column(length=1000)
    private String description;

    @Column(nullable=false)
    private LocalDateTime submittedAt;

    @ManyToOne
    @JoinColumn(name="project_group_id", nullable=false)
    private ProjectGroup projectGroup;

    @ManyToOne
    @JoinColumn(name="submitted_by", nullable=false)
    private Student submittedBy;

    private Integer marks;

    @Column(length=500)
    private String feedback;

    @ManyToOne
    @JoinColumn(name="evaluated_by")
    private Teacher evaluatedBy;

    private LocalDateTime evaluatedAt;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public ProjectGroup getProjectGroup() {
        return projectGroup;
    }

    public void setProjectGroup(ProjectGroup projectGroup) {
        this.projectGroup = projectGroup;
    }

    public Student getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(Student submittedBy) {
        this.submittedBy = submittedBy;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Teacher getEvaluatedBy() {
        return evaluatedBy;
    }

    public void setEvaluatedBy(Teacher evaluatedBy) {
        this.evaluatedBy = evaluatedBy;
    }

    public LocalDateTime getEvaluatedAt() {
        return evaluatedAt;
    }

    public void setEvaluatedAt(LocalDateTime evaluatedAt) {
        this.evaluatedAt = evaluatedAt;
    }
}