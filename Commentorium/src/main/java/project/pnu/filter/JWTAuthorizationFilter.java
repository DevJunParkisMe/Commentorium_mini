package project.pnu.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import project.pnu.domain.Member;
import project.pnu.persistence.MemberRepository;

@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter{
	
	private final MemberRepository memRepo;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String srcToken = request.getHeader("Authorization");
		
		if (srcToken == null || !srcToken.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String jwtToken = srcToken.replace("Bearer ", "");
		
		String username = JWT.require(Algorithm.HMAC256("edu.pnu.jwt")).build().verify(jwtToken).getClaim("username").asString();
		
		Optional<Member> opt = memRepo.findById(username);
		
		if (!opt.isPresent()) {
			filterChain.doFilter(request, response);
			return;
		}
		
		Member findMember = opt.get();
		
		User user = new User(findMember.getId(), findMember.getPwd(), AuthorityUtils.createAuthorityList(findMember.getRole().toString()));
		
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		filterChain.doFilter(request, response);
	}
	
}
