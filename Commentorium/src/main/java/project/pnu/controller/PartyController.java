package project.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.pnu.domain.Party;
import project.pnu.service.PartyService;

@RestController
@RequestMapping("/api/party")
public class PartyController {

	@Autowired
	private PartyService partyService;
	
	@PostMapping("/add")
	@CrossOrigin(origins="*")
	public Party insertParty(@RequestBody Party party) {	
		return partyService.insertParty(party);
	}
	
	@GetMapping("/")
	@CrossOrigin(origins="*")
	public List<Party> getAllParty() {
		return partyService.getAllParty();
	}

	
	@DeleteMapping("/delete/{seq}")
	@CrossOrigin(origins="*")
	public ResponseEntity<?> deleteParty(@PathVariable Long seq, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
		if (token != null && token.startsWith("Bearer ")) {
			String jwtToken = token.substring(7); // "Bearer " 다음의 부분을 추출
			
			boolean isDelete = partyService.deleteParty(seq, jwtToken);
			
			if (isDelete)
				return ResponseEntity.ok("");
			else 
				return ResponseEntity.badRequest().body("");
		}
		return ResponseEntity.badRequest().body("");
	}
	
	@PutMapping("/update/{seq}")
	@CrossOrigin(origins="*")
	public ResponseEntity<?> updateParty(@RequestBody Party party, @PathVariable Long seq, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
		if (token != null && token.startsWith("Bearer ")) {
			String jwtToken = token.substring(7); // "Bearer " 다음의 부분을 추출
			
			boolean isUpdate = partyService.updateParty(party, seq, jwtToken);
			
			if (isUpdate) { 
				return ResponseEntity.ok("");
			}
			
			else {
				return ResponseEntity.badRequest().body("");
			}
		}
		return ResponseEntity.badRequest().body("");
		
	}
	
}
