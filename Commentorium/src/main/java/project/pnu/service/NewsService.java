package project.pnu.service;

import java.util.List;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import project.pnu.config.JsoupConnection;
import project.pnu.domain.News;
import project.pnu.persistence.NewsRepository;

@Service
@Transactional
public class NewsService {

	@Autowired
	private NewsRepository newsRepo;
	
	
	@Autowired
	private JsoupConnection jsoupConnection;

	public void setNewsElements() {
		try {
			for (int i=1; i<=72; i++) {
				String url_news = String.format("https://www.inven.co.kr/webzine/news/?hotnews=1&page=%d", i);
				Connection con = jsoupConnection.getJsoupConnection(url_news);
				con.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");
				Document document = con.get();
				
				Elements titleEls = document.select("span.title");
				Elements summaryEls = document.select("span.summary");
				Elements imgEls = document.select("img.banner");
				Elements hyperLinkEls = document.select("div.content > a");
//				for (Element hyperLinkEl: hyperLinkEls) {
//					System.out.println(summary.wholeOwnText().strip());
//					String srcValue = imgEl.attr("src");
//					System.out.println(srcValue);
//					String hrefValue = hyperLinkEl.attr("href");
//					System.out.println(hrefValue);
//				}
				
				for (int j=0; j<=10; j++) {
					News tempNews = News.builder()
										.title(titleEls.get(j).wholeOwnText())
										.summary(summaryEls.get(j).wholeOwnText().length() >= 255 ? summaryEls.get(j).wholeOwnText().substring(0,253) : summaryEls.get(j).wholeOwnText())
										.imgUrl(imgEls.get(j).attr("src"))
										.hyperLink(hyperLinkEls.get(j).attr("href"))
										.build();
					newsRepo.save(tempNews);
					
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
	}
	
	public List<News> getAllNews() {
		return newsRepo.findAll();
	}
}
