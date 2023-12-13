package project.pnu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import project.pnu.domain.Member;
import project.pnu.persistence.MemberRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private MemberRepository memRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memRepo.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
		return new User(member.getId(),member.getPwd(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
	}
}
