package com.nguyen.blogs.Entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_comment")
public class Comment implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 11)
	private Integer id;
	
	/*
	 * nếu muốn reply 1 comment trong bài post thì thêm 
	 * @Column(name = "id_reply", length = 11)
	 * private Integer idReply;
	 * mặc định là 0 nếu chỉ reply bài post
	 * nếu reply 1 comment trong bài post thì idReply = id comment.
	 * */
	@Column(name = "id_reply", length = 11)
	private Integer idReply;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
	@Column(name = "cm_content", nullable = false)
	private String commentContent;
	
	@Column(name = "datetime", nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dateTime;
	
	
	public Comment() {
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdReply() {
		return idReply;
	}
	public void setIdReply(Integer idReply) {
		this.idReply = idReply;
	}
	
	@JsonIgnore
    public Post getPost() {
        return post;
    }
	
	public Post setPost(Post post) {
		return this.post = post;
	}
	
	@JsonIgnore
    public User getUser() {
        return user;
    }
	
	public User setUser(User user) {
		return this.user = user;
	}
	
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
}
