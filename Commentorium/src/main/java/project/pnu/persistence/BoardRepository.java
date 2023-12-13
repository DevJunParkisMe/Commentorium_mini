package project.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import project.pnu.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
