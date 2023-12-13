package project.pnu.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;

import jakarta.transaction.Transactional;
import project.pnu.domain.Board;
import project.pnu.domain.Comment;
import project.pnu.domain.Member;
import project.pnu.persistence.BoardRepository;
import project.pnu.persistence.CommentRepository;
import project.pnu.persistence.MemberRepository;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private MemberRepository memRepo;
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Override
	public void insertComment(Comment comment, Long boardId) {
		  Optional<Board> optionalBoard = boardRepo.findById(boardId);
		    if (optionalBoard.isPresent()) {
		        Board selectedBoard = optionalBoard.get();
		        comment.setBoardId(boardId);
		        Optional<Member> optionalMember = memRepo.findById(comment.getMemId());
		        if (optionalMember.isPresent()) {
		            Member findMember = optionalMember.get();
		            String trimString = comment.getContent().trim();
		            comment.setMember(findMember);
		            comment.setMemId(findMember.getId());
		            
		            if (trimString != "") {
		            	commentRepo.save(comment);
		            }
		            else {
		            	System.out.println("게시글 등록 실패: 빈 문자열");
		            }
		        } else {	
		            // 회원을 찾을 수 없을 때의 처리
		        	System.out.println("게시글 등록 실패: 회원을 찾을 수 없음");
		        }
		    } else {
		        // 게시판을 찾을 수 없을 때의 처리
		    	System.out.println("게시글 등록 실패: 존재하지 않는 게시판");
		    }
	}

	@Override
	public boolean updateComment(Comment comment, Long seq, String token) {
		Map<String, Claim> test = JWT.decode(token).getClaims();
		Member findMember = memRepo.findById(test.get("username").asString()).get();
		System.out.println("findMember id : "+ findMember.getId());
		if (commentRepo.existsById(seq)) {
			Comment findComment = commentRepo.findById(seq).get();
			System.out.println("findComment : " + findComment);
			if (findComment.getMember().getId() == findMember.getId()) {
				
				findComment.setContent(comment.getContent());
				commentRepo.save(findComment);
				return true;
			}
			else {
				System.out.println("ID가 일치하지 않습니다");
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean deleteComment(Long seq, String token) {
		Map<String, Claim> test = JWT.decode(token).getClaims();
		Member findMember = memRepo.findById(test.get("username").asString()).get();
		System.out.println("findMember : " + findMember.toString());
		
		if(commentRepo.existsById(seq)) {
			Comment findComment = commentRepo.findById(seq).get();
			if (findComment.getMember().getId() == findMember.getId()) {
				findComment.getMember().getCommentList().remove(findComment);
				memRepo.save(findComment.getMember());
				commentRepo.delete(findComment);
				
				return true;
			}
		}
//		if (optionalComment.isPresent()) {
//			Comment comment = optionalComment.get();
//			System.out.println("comment : " + comment);
//			if (comment.getMemId() == findMember.getId()) {
//				Member member = comment.getMember();
//				System.out.println("실행했니? 1");
//				if (member != null) {
//					member.getCommentList().remove(comment);
//				}
//				System.out.println("실행했니? 2");
//				// Member와의 연관 관계에서 댓글을 제거
//				commentRepo.deleteById(seq);
//				
//				return true;
//			}
//			return false;
//		}
		return false;
	}

	@Override
	public List<Comment> getCommentList(Long boardId) {
		 Optional<Board> optionalBoard = boardRepo.findById(boardId);
		    if (optionalBoard.isPresent()) {
		        List<Comment> tempList = commentRepo.findByBoardId(boardId);
		        
		        System.out.println("tempList : " + tempList);
		        
		        for (Comment comment : tempList) {
		            comment.setMemId(comment.getMember().getId());
		        }
		        
		        return tempList;
		    } else {
		        // 게시판을 찾을 수 없을 때의 처리
		        return Collections.emptyList(); // 또는 null 또는 다른 적절한 값을 반환
		    }
	}

}
