package com.klef.sdp.backend.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name="project_table")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length=100, nullable=false)
    private String title;

    @Column(length=255, nullable=false)
    private String description;

    @Column(nullable=false)
    private LocalDate deadline;

    private String fileName;
    private String filePath;

    @ManyToOne
    @JoinColumn(name="subject_code", nullable=false)
    @JsonIgnoreProperties({"teacher", "projects"})
    private Subject subject;

    @ManyToOne
    @JoinColumn(name="teacher_id", nullable=false)
    @JsonIgnoreProperties({"subjects", "projects"})
    private Teacher teacher;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }
}