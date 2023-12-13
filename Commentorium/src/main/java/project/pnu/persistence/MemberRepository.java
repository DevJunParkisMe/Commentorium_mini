package project.pnu.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import project.pnu.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
}
