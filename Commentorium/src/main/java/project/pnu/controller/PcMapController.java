package project.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.pnu.domain.PcMap;
import project.pnu.service.PcMapService;

@RestController
@RequestMapping("/api/map")
public class PcMapController {
	
	@Autowired
	private PcMapService mapService;
	
	@PostMapping("/")
	@CrossOrigin(origins="*")
	public void addMapData() {
		String csvFilePath = "D:\\data\\20230927_GJ_PC.csv";
		mapService.insertCsvToDatabase(csvFilePath);
	}
//	
//	@GetMapping("/test")
//	public void getLatLng() {
//		mapService.getLatLng();
//	}
	
	@GetMapping("/get")
	@CrossOrigin(origins="*")
	public List<PcMap> getMapData() {
		return mapService.getMapData();
	}
}
