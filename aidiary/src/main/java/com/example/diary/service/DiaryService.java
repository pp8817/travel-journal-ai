package com.example.diary.service;

import com.example.diary.dto.DiaryResponse;
import com.example.diary.util.InputFileResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DiaryService {

    public DiaryResponse createDiaryWithAI(MultipartFile[] images,
                                           String travelDate,
                                           String travelLocation,
                                           String feeling,
                                           String companion,
                                           String weather) throws IOException {

        if (images == null || images.length == 0) {
            throw new IllegalArgumentException("이미지는 필수입니다.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("travelDate", travelDate);
        body.add("travelLocation", travelLocation);
        body.add("feeling", feeling);
        body.add("companion", companion);
        body.add("weather", weather);

        for (MultipartFile image : images) {
            Resource fileResource = new InputFileResource(image.getInputStream(), image.getOriginalFilename());
            body.add("images", fileResource);
        }

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        String aiServerUrl = "http://AI"; // 실제 주소로 교체해야함

        ResponseEntity<String> response = restTemplate.postForEntity(aiServerUrl, requestEntity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.getBody(), DiaryResponse.class);
    }
}
