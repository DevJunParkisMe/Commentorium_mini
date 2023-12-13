package project.pnu.service;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;

import jakarta.transaction.Transactional;
import project.pnu.domain.Member;
import project.pnu.persistence.CommentRepository;
import project.pnu.persistence.MemberRepository;

@Service
@Transactional
public class MemberService {

	@Autowired
	private MemberRepository memRepo;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private CommentRepository commentRepo;
	private static final Logger logger = LoggerFactory.getLogger(Member.class);

	public boolean insertMember(Member member) {
		if (memRepo.existsById(member.getId())) {
			return false;
		} else {
			System.out.println("여기까지 왔음?");
			System.out.println("Member : " + member.toString());
			member.setPwd(encoder.encode(member.getPwd()));
			memRepo.save(member);
			return true;
		}

	}

	public Member getMember(String token) {
		System.out.println("실행 했니? 7");
		Map<String, Claim> test = JWT.decode(token).getClaims();
		Member member = memRepo.findById(test.get("username").asString()).get();
		return member;
		
		
	}

	public boolean deleteMember(String token) {
		try {
			System.out.println("token : " + token);
			Map<String, Claim> test = JWT.decode(token).getClaims();
			Member findMember = memRepo.findById(test.get("username").asString()).get();
			if (memRepo.existsById(findMember.getId())) {
				// 참조하는 댓글을 모두 삭제
				findMember.getCommentList().clear(); // 댓글 리스트를 비웁니다.
				findMember.getPartyList().clear();
				memRepo.deleteById(findMember.getId());
				return true;
			} else {
				logger.warn("Member with ID {} not found", findMember.getId());
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void updateMember(String token, Member member) {
		Map<String, Claim> test = JWT.decode(token).getClaims();
		Member findMember = memRepo.findById(test.get("username").asString()).get();
		findMember.setPwd(encoder.encode(member.getPwd()));
		findMember.setEmail(member.getEmail());
		findMember.setName(member.getName());

		memRepo.save(findMember);
	}
}
