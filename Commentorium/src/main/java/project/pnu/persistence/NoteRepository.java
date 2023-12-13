package project.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import project.pnu.domain.Member;
import project.pnu.domain.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
