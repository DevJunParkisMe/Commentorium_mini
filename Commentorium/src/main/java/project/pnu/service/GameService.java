package project.pnu.service;

import java.util.List;

import project.pnu.domain.Game;
import project.pnu.domain.GameDetail;

public interface GameService {
	void setElements();
	
	List<Game> getElements();
	
	List<GameDetail> getDetail();
}
