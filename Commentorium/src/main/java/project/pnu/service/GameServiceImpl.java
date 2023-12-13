package project.pnu.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import project.pnu.config.JsoupConnection;
import project.pnu.domain.Board;
import project.pnu.domain.Game;
import project.pnu.domain.GameDetail;
import project.pnu.persistence.BoardRepository;
import project.pnu.persistence.GameDetailRepository;
import project.pnu.persistence.GameRepository;

@Service
@Transactional
public class GameServiceImpl implements GameService {

	@Autowired
	private GameRepository gameRepo;

	@Autowired
	private GameDetailRepository gameDetailRepo;
	
	@Autowired
	private BoardRepository boardRepo;
	
	
	@Autowired
	private JsoupConnection jsoupConnection;

	@Override
	public void setElements() {
		String url_rank = "http://www.gametrics.com/rank/Rank02.aspx";
		List<List<String>> tempTotalList = new ArrayList<List<String>>();
		
		try {
			Connection con = jsoupConnection.getJsoupConnection(url_rank);
			
			Document document = con.get();
			for (int i = 0; i < 10; i++) {
				List<String> tempTdList = new ArrayList<String>();	
				Elements trElements = document.select(String.format("tr#rp_rank_ctl0%d_tr_item", i));
				Elements tdElements = trElements.select("td");
				for (Element tdElement: tdElements) {
					
					String text = tdElement.text().trim();	
					if (!text.isEmpty()) {
						tempTdList.add(text);
					}
					
				}
				if (tempTdList.size() != 5) {
					tempTdList.add(1, "0");
				}
				tempTotalList.add(tempTdList);
			}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (List<String> tdList: tempTotalList) {
			Game game = Game.builder()
							.rank(Long.parseLong(tdList.get(0)))
							.change(Long.parseLong(tdList.get(1)))
							.name(tdList.get(2))
							.share(tdList.get(4))
							.genre(tdList.get(3))
							.build();
							
			gameRepo.save(game);
			
			Board board = Board.builder()
								.name(tdList.get(2))
								.build();
			
			boardRepo.save(board);
		}
	}

	@Override
	public List<Game> getElements() {
		return gameRepo.findAll();
	}

	
	@Override
	public List<GameDetail> getDetail() {
		return gameDetailRepo.findAll();
	}
}
