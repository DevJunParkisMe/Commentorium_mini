package project.pnu.config;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class KakaoMapConfig {
	
	
	public void getLatLng() {
		   // 카카오맵 API 키
	    String apiKey = "0f4535dec43a7b5a0b80429f9ce03163";

	    // 변환하려는 주소
	    String address = "부산광역시 금정구 서동로149번길 16 (서동)";

	    // 주소를 위도와 경도로 변환
	    try {
	        String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json?query=" + address;
	        OkHttpClient client = new OkHttpClient();

	        Request request = new Request.Builder()
	                .url(apiUrl)
	                .addHeader("Authorization", "KakaoAK " + apiKey)
	                .build();
	        System.out.println("request : " + request);
	        Response response = client.newCall(request).execute();
	        System.out.println("response : " + response);
	        String responseBody = response.body().string();
	        System.out.println("responseBody : " + responseBody);
	        JSONObject jsonObject = new JSONObject(responseBody);
	        JSONArray documents = jsonObject.getJSONArray("documents");

	        if (documents.length() > 0) {
	            double latitude = documents.getJSONObject(0).getDouble("y");
	            double longitude = documents.getJSONObject(0).getDouble("x");

	            System.out.println("주소: " + address);
	            System.out.println("위도: " + latitude);
	            System.out.println("경도: " + longitude);
	        } else {
	            System.out.println("위도와 경도를 찾을 수 없습니다.");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}

