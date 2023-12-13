package project.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.pnu.domain.Game;
import project.pnu.domain.GameDetail;
import project.pnu.persistence.GameRepository;
import project.pnu.persistence.NewsRepository;
import project.pnu.service.GameService;
import project.pnu.service.NewsService;

@RestController
@RequestMapping("/api")
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private GameRepository gameRepo;
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private NewsRepository newsRepo;
	
	@PostMapping("/")
	@CrossOrigin(origins="*")
	public void setElements() {
		if (gameRepo.count() == 0) {
			gameService.setElements();
		}
		if (newsRepo.count() == 0) {
			newsService.setNewsElements();
		}
	}
	
	@GetMapping("/")
	@CrossOrigin(origins="*")
	public List<Game> getElements() {
		return gameService.getElements();
	}
	
	@GetMapping("/game/detail")
	@CrossOrigin(origins="*")
	public List<GameDetail> getDetail() {
		return gameService.getDetail();
	}
	
}
