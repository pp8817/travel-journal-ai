package com.travel.global.util;

import com.travel.domain.diary.dto.response.PinResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ImageUtil {

    public List<String> saveImages(List<MultipartFile> images, String uploadDir) {
        List<String> savedPaths = new ArrayList<>();
        for (MultipartFile image : images) {
            try {
                String path = FileUtil.saveImageToLocal(image, uploadDir);
                savedPaths.add(path);
            } catch (IOException e) {
                throw new RuntimeException("이미지 저장 실패", e);
            }
        }
        return savedPaths;
    }

    public String encodeFirstImageToBase64(List<MultipartFile> images) {
        if (images.isEmpty()) return null;
        MultipartFile first = images.get(0);
        try {
            byte[] bytes = first.getBytes();
            return "data:" + first.getContentType() + ";base64," + Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            throw new RuntimeException("Base64 인코딩 실패", e);
        }
    }

    public List<PinResponse> extractMetadata(List<MultipartFile> images) {
        return ExifUtil.extractImageMetadata(images).stream()
                .map(meta -> new PinResponse(meta.latitude(), meta.longitude(), meta.timestamp(), meta.fileName()))
                .toList();
    }
}