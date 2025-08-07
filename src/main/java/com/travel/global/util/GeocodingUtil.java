package com.travel.global.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class GeocodingUtil {

    @Value("${google.maps.api-key}")
    private String apiKey;

    public String getLocation(double lat, double lon) {
        try {
            String urlStr = String.format(
                    "https://maps.googleapis.com/maps/api/geocode/json?latlng=%f,%f&key=%s&language=ko",
                    lat, lon, apiKey
            );

            URL url = new URL(urlStr);
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

            JSONObject json = new JSONObject(response.toString());

            // ✅ API 상태 확인
            if (!json.getString("status").equals("OK")) {
                return "Unknown Location";
            }

            JSONArray results = json.getJSONArray("results");
            if (results.length() == 0) return "Unknown Location";

            JSONObject firstResult = results.getJSONObject(0);
            JSONArray components = firstResult.getJSONArray("address_components");

            // ⬇️ 우선순위대로 담을 Map (중복 제거용)
            Map<String, String> locationMap = new LinkedHashMap<>();

            for (int i = 0; i < components.length(); i++) {
                JSONObject comp = components.getJSONObject(i);
                JSONArray types = comp.getJSONArray("types");

                for (int j = 0; j < types.length(); j++) {
                    String type = types.getString(j);
                    String name = comp.getString("long_name");

                    switch (type) {
                        case "sublocality":
                        case "locality":
                        case "administrative_area_level_2":
                        case "administrative_area_level_1":
                        case "country":
                            locationMap.putIfAbsent(type, name);
                            break;
                    }
                }
            }

            // ⬇️ 우선순위대로 구성
            String[] priority = {
                    "sublocality",
                    "locality",
                    "administrative_area_level_2",
                    "administrative_area_level_1",
                    "country"
            };

            StringBuilder fullAddress = new StringBuilder();
            for (String key : priority) {
                String val = locationMap.get(key);
                if (val != null && fullAddress.indexOf(val) == -1) {
                    if (fullAddress.length() > 0) fullAddress.append(", ");
                    fullAddress.append(val);
                }
            }

            return fullAddress.toString().isEmpty() ? "Unknown Location" : fullAddress.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown Location";
        }
    }
}