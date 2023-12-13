package project.pnu.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import project.pnu.config.KakaoMapConfig;
import project.pnu.domain.PcMap;
import project.pnu.persistence.PcMapRepository;

@Service
@Transactional
public class PcMapService {
	@Autowired
	private PcMapRepository mapRepo;
	
	@Autowired
	private KakaoMapConfig kakaoMap;
	
	public void insertCsvToDatabase(String csvFilePath) {
		
		if (mapRepo.count() == 0) {
			try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFilePath), "UTF-8"))) {
				boolean firstLine = true; // 첫 번째 행 여부를 확인하기 위한 변수
	            String line;
	            while ((line = br.readLine()) != null) {
	            	if (firstLine) {
	                    firstLine = false;
	                    continue; // 첫 번째 행은 건너뛰고 다음 라인으로 진행
	                }
	                String[] values = line.split(",");
	                
	                PcMap pcMap = new PcMap();
	                pcMap.setName(values[0]);
	                pcMap.setAddress(values[1].replace("\"", ""));
	                
	                mapRepo.save(pcMap);
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
	
	public List<PcMap> getMapData() {
		return mapRepo.findAll();
	}
	
	
	public void getLatLng() {
		kakaoMap.getLatLng();
	}
}
