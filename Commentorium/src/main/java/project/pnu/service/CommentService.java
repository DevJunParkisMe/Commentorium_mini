package project.pnu.service;

import java.util.List;

import project.pnu.domain.Comment;
import project.pnu.domain.Game;

public interface CommentService {
	
	void insertComment(Comment comment, Long boardId);
	
	boolean updateComment(Comment comment, Long seq, String token);
	
	boolean deleteComment(Long seq, String token);
	
	List<Comment> getCommentList(Long boardId);
	
}
