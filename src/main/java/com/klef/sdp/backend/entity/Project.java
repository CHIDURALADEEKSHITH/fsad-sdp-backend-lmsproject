
package com.klef.sdp.backend.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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
	    
	    @ManyToOne
	    @JoinColumn(name="subject_code", nullable=false)
	    private Subject subject;

	    @ManyToOne
	    @JoinColumn(name="teacher_id", nullable=false)
	    private Teacher teacher; //

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public LocalDate getDeadline() {
			return deadline;
		}

		public void setDeadline(LocalDate deadline) {
			this.deadline = deadline;
		}
		

		public Subject getSubject() {
			return subject;
		}

		public void setSubject(Subject subject) {
			this.subject = subject;
		}

		public Teacher getTeacher() {
			return teacher;
		}

		public void setTeacher(Teacher teacher) {
			this.teacher = teacher;
		}

		@Override
		public String toString() {
			return "Project [id=" + id + ", title=" + title + ", description=" + description + ", deadline=" + deadline
					+ "]";
		}
}
