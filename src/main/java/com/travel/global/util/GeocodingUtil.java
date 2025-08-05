package com.travel.global.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class GeocodingUtil {

    @Value("${google.maps.api-key}")
    private String apiKey;

    public String getLocation(double lat, double lon) {
        try {
            String url = String.format(
                    "https://maps.googleapis.com/maps/api/geocode/json?latlng=%f,%f&key=%s",
                    lat, lon, apiKey
            );

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            JSONObject json = new JSONObject(response.toString());
            JSONArray results = json.getJSONArray("results");
            if (results.length() == 0) return "Unknown Location";

            JSONObject address = results.getJSONObject(0);
            JSONArray components = address.getJSONArray("address_components");

            String city = "";
            String country = "";

            for (int i = 0; i < components.length(); i++) {
                JSONObject comp = components.getJSONObject(i);
                JSONArray types = comp.getJSONArray("types");

                for (int j = 0; j < types.length(); j++) {
                    String type = types.getString(j);

                    // 도시 추출 우선순위: locality > sublocality > administrative_area_level_1
                    if ((type.equals("locality") || type.equals("sublocality") || type.equals("administrative_area_level_1"))
                            && city.isEmpty()) {
                        city = comp.getString("long_name");
                    }

                    if (type.equals("country")) {
                        country = comp.getString("long_name");
                    }
                }
            }

            return (city.isEmpty() ? "" : city + ", ") + (country.isEmpty() ? "Unknown Country" : country);

        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown Location";
        }
    }
}