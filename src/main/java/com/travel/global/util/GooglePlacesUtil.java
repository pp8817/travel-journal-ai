package com.travel.global.util;

import com.travel.domain.image.dto.PlaceInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class GooglePlacesUtil {

    @Value("${google.maps.api-key}")
    private String apiKey;

    public PlaceInfo getExactPlaceInfo(double lat, double lon) {
        try {
            // 1️⃣ Nearby Search: 가장 가까운 장소의 place_id 가져오기
            String nearbyUrlStr = String.format(
                    "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%f,%f&rankby=distance&key=%s&language=ko",
                    lat, lon, apiKey
            );
            JSONObject nearbyJson = sendGetRequest(nearbyUrlStr);
            System.out.println("🔎 Nearby Search 응답 status: " + nearbyJson.optString("status"));

            JSONArray results = nearbyJson.optJSONArray("results");
            if (results == null || results.isEmpty()) {
                return new PlaceInfo("Unknown Place", "Unknown Address", 0.0, 0);
            }

            JSONObject topResult = results.getJSONObject(0);
            String placeId = topResult.optString("place_id", null);
            if (placeId == null) {
                return new PlaceInfo("Unknown Place", "Unknown Address", 0.0, 0);
            }

            // 2️⃣ Place Details: 정확한 장소 정보 조회
            String detailUrlStr = String.format(
                    "https://maps.googleapis.com/maps/api/place/details/json?place_id=%s&key=%s&language=ko",
                    placeId, apiKey
            );
            JSONObject detailJson = sendGetRequest(detailUrlStr);
            JSONObject detail = detailJson.optJSONObject("result");
            if (detail == null) {
                return new PlaceInfo("Unknown Place", "Unknown Address", 0.0, 0);
            }

            String name = detail.optString("name", "Unknown Place");
            String address = detail.optString("formatted_address", "Unknown Address");
            double rating = detail.optDouble("rating", 0.0);
            int userRatingsTotal = detail.optInt("user_ratings_total", 0);

            System.out.println("✅ 장소 이름: " + name);
            System.out.println("✅ 주소: " + address);
            System.out.println("✅ 평점: " + rating);
            System.out.println("✅ 리뷰 수: " + userRatingsTotal);

            return new PlaceInfo(name, address, rating, userRatingsTotal);

        } catch (Exception e) {
            System.out.println("❗ 예외 발생: " + e.getMessage());
            e.printStackTrace();
            return new PlaceInfo("Unknown Place", "Unknown Address", 0.0, 0);
        }
    }

    private JSONObject sendGetRequest(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        System.out.println("📍 요청 URL: " + url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(3000);
        conn.setReadTimeout(3000);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        return new JSONObject(response.toString());
    }
}