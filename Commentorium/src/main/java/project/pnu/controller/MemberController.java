package project.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.pnu.domain.Member;
import project.pnu.domain.TokenResponse;
import project.pnu.service.MemberService;

@RestController
@RequestMapping("/api/user")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@PostMapping("/add")
	@CrossOrigin(origins = "*")
	public ResponseEntity<String> insertMember(@RequestBody Member member) {
		System.out.println("MemberController member : " + member.toString());
		boolean isInsertSuccessful = memberService.insertMember(member);

		if (isInsertSuccessful) {
			return ResponseEntity.ok("회원가입 성공");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패");
		}
	}
	@GetMapping("/get")
	@CrossOrigin(origins="*")
	public ResponseEntity<?> getMember(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
		try {
			// 토큰 검증 로직을 추가하고, 유효한 토큰인지 확인
			if (token != null && token.startsWith("Bearer ")) {
				String jwtToken = token.substring(7); // "Bearer " 다음의 부분을 추출

				Member member = memberService.getMember(jwtToken);

				return ResponseEntity.ok(member);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 토큰이 유효하지 않습니다");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
		}
	}

	@DeleteMapping("/delete")
	@CrossOrigin(origins = "*")
	public ResponseEntity<?> deleteMember(@RequestBody Member member,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
		try {
			// 토큰 검증 로직을 추가하고, 유효한 토큰인지 확인
			if (token != null && token.startsWith("Bearer ")) {
				String jwtToken = token.substring(7); // "Bearer " 다음의 부분을 추출
				// JWT 검증 로직을 수행 (예: 토큰 만료 여부, 서명 검증 등)

				// 토큰이 유효하면 삭제 로직 수행
				System.out.println("Member delete : " + member);
				boolean isDeleteSuccessful = memberService.deleteMember(jwtToken);

				if (isDeleteSuccessful) {
					return ResponseEntity.ok("회원 탈퇴 성공");
				} else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 탈퇴 실패");
				}
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 토큰이 유효하지 않습니다");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
		}
	}

	@PutMapping("/update")
	@CrossOrigin(origins = "*")
	public ResponseEntity<?> updateMember(@RequestBody Member member,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
		try {
			// 토큰 검증 로직을 추가하고, 유효한 토큰인지 확인
			if (token != null && token.startsWith("Bearer ")) {
				String jwtToken = token.substring(7); // "Bearer " 다음의 부분을 추출
				// JWT 검증 로직을 수행 (예: 토큰 만료 여부, 서명 검증 등)

				// 토큰이 유효하면 수정 로직 수행
				memberService.updateMember(jwtToken, member);

				return ResponseEntity.ok("회원 수정 성공");
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 토큰이 유효하지 않습니다");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
		}
	}

}
