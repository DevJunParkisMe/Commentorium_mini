package project.pnu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import project.pnu.domain.Member;
import project.pnu.domain.Note;
import project.pnu.persistence.MemberRepository;
import project.pnu.persistence.NoteRepository;

@Service
@Transactional
public class NoteService {
	
	@Autowired
	private NoteRepository noteRepository;
	

	
	public void addNote(Note note) {
		noteRepository.save(note);
	}
	
	public List<Note> getNoteList(String id) {
	
		return null;
	}
	
}
