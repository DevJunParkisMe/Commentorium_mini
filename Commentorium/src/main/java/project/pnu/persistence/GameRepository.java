package project.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import project.pnu.domain.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

}
