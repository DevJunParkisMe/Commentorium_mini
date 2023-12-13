package project.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.pnu.domain.Note;
import project.pnu.service.NoteService;

@RestController
@RequestMapping("/api/note")
public class NoteController {
	
	@Autowired
	private NoteService noteService;
	
	@PostMapping("/add")
	@CrossOrigin(origins="*")
	public ResponseEntity<?> addNote(@RequestBody Note note) {
		noteService.addNote(note);
		
		return ResponseEntity.ok("쪽지 등록 완료");
	}
	
	@GetMapping("/")
	@CrossOrigin(origins="*")
	public ResponseEntity<?> getNoteList(@RequestHeader(name="id") String id) {
		List<Note> noteList = noteService.getNoteList(id);
		return ResponseEntity.ok(noteList);	
	}
}
