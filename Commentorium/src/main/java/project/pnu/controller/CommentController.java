package project.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.pnu.domain.Comment;
import project.pnu.service.CommentService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	@Autowired
	private CommentService commentService;

	@GetMapping("/get/{seq}")
	@CrossOrigin(origins = "*")
	public List<Comment> getCommentList(@PathVariable Long seq) {
		return commentService.getCommentList(seq);
	}

	@PostMapping("/add/{boardId}")
	@CrossOrigin(origins = "*")
	public void insertComment(@RequestBody Comment comment, @PathVariable Long boardId) {

		System.out.println("insertComment : " + comment);
		commentService.insertComment(comment, boardId);
	}

	@DeleteMapping("/delete/{seq}")
	@CrossOrigin(origins="*")
	public ResponseEntity<?> deleteComment(@PathVariable Long seq, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
		if (token != null && token.startsWith("Bearer ")) {
			String jwtToken = token.substring(7); // "Bearer " 다음의 부분을 추출
			boolean isDelete = commentService.deleteComment(seq, jwtToken);
			
			if (isDelete) {
				return ResponseEntity.ok("");
			}
			else {
				return ResponseEntity.badRequest().body("");
			}
		
		}
		return ResponseEntity.badRequest().body("");
		
	}

	@PutMapping("/update/{seq}")
	@CrossOrigin(origins = "*")
	public ResponseEntity<?> updateComment(@RequestBody Comment comment, @PathVariable Long seq, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
		if (token != null && token.startsWith("Bearer ")) {
			String jwtToken = token.substring(7); // "Bearer " 다음의 부분을 추출
			
			System.out.println("comment :" + comment);
			System.out.println("seq : " + seq);
			boolean isUpdate = commentService.updateComment(comment, seq, jwtToken);
			
			if (isUpdate) {
				return ResponseEntity.ok("");
			}
			else {
				return ResponseEntity.badRequest().body("");
			}
		}
		return ResponseEntity.badRequest().body("");
	}
}
