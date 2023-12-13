package project.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.pnu.domain.News;
import project.pnu.service.NewsService;

@RestController
@RequestMapping("/api/news")
public class NewsController {
	
	@Autowired
	private NewsService newsService;
	
	@GetMapping("/")
	@CrossOrigin(origins="*")
	public List<News> getAllNews() {
		return newsService.getAllNews();	
	}
	
}
