package project.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import project.pnu.domain.Party;

public interface PartyRepository extends JpaRepository<Party, Long> {

}
