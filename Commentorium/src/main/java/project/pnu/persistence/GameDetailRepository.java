package project.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import project.pnu.domain.GameDetail;

public interface GameDetailRepository extends JpaRepository<GameDetail, Long> {

}
