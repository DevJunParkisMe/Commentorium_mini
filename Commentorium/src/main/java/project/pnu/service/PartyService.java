package project.pnu.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;

import jakarta.transaction.Transactional;
import project.pnu.domain.Member;
import project.pnu.domain.Party;
import project.pnu.persistence.MemberRepository;
import project.pnu.persistence.PartyRepository;

@Service
@Transactional
public class PartyService {

	@Autowired
	private PartyRepository partyRepo;
	
	@Autowired
	private MemberRepository memRepo;
	
	public Party insertParty(Party party) {
		Member findMember = memRepo.findById(party.getMemId()).get();
		party.setMember(findMember);
		return partyRepo.save(party);
	}
	
	public List<Party> getAllParty() {
		List<Party> tempList = partyRepo.findAll();
		for (int i=0; i<tempList.size(); i++) {
			tempList.get(i).setMemId(tempList.get(i).getMember().getId());
		}
		return tempList;
	}
	
	public boolean deleteParty(Long seq, String token) {
		Map<String, Claim> test = JWT.decode(token).getClaims();
		Member findMember = memRepo.findById(test.get("username").asString()).get();
		Optional<Party> findParty = partyRepo.findById(seq);
		if (findParty.isPresent()) {
			Member member = findParty.get().getMember();
			if (member.getId() == findMember.getId()) {
				member.getPartyList().remove(findParty.get());
				memRepo.save(member);
				partyRepo.deleteById(seq);
				
				return true;
			}
		}
		return false;
	}
	
	public boolean updateParty(Party party, Long seq, String token) {
		Map<String, Claim> test = JWT.decode(token).getClaims();
		Member findMember = memRepo.findById(test.get("username").asString()).get();
		Optional<Party> tempParty = partyRepo.findById(seq);
		if (tempParty.isPresent()) {
			Party findParty = tempParty.get();
			if (findParty.getMember().getId() == findMember.getId()) {
				findParty.setTitle(party.getTitle());
				findParty.setContent(party.getContent());
				findParty.setCategory(party.getCategory());
				partyRepo.save(findParty);
				
				return true;
			}
		}
		return false;
	}
}
