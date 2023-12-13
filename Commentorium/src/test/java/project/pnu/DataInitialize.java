package project.pnu;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.pnu.domain.GameDetail;
import project.pnu.persistence.GameDetailRepository;

@SpringBootTest
public class DataInitialize {
	@Autowired
	private GameDetailRepository gameDetailRepo;
	
	@Test
	public void insertCsvtoDatabase() {
		if (gameDetailRepo.count() == 0) {
			try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\data\\detail.csv"), "UTF-8"))) {
//				boolean firstLine = true; // 첫 번째 행 여부를 확인하기 위한 변수
	            String line;
	            while ((line = br.readLine()) != null) {
//	            	if (firstLine) {
//	                    firstLine = false;
//	                    continue; // 첫 번째 행은 건너뛰고 다음 라인으로 진행
//	                }
	                String[] values = line.split(",");
	                
	                GameDetail pcMap = new GameDetail();
	                pcMap.setName(values[2]);
	                pcMap.setDistributor(values[0]);
	                pcMap.setGenre(values[1]);
	                pcMap.setOpenDate(values[3]);
	                pcMap.setProducer(values[4]);
	                pcMap.setRating(values[5]);
	                
	                gameDetailRepo.save(pcMap);
	            }

	            System.out.println("Data inserted successfully.");

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		else {
			System.out.println("Data already inserted");
		}
        
	}
}
