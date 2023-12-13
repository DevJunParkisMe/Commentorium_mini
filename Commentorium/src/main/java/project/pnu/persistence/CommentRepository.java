package project.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import project.pnu.domain.Board;
import project.pnu.domain.Comment;
import project.pnu.domain.Member;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findBySeq(Long seq);
	List<Comment> findByBoardId(Long boardId);
	
	void deleteByMember(Member member);
}
