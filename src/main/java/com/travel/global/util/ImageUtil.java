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

    public static List<String> saveImages(List<MultipartFile> images, String uploadDir) {
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile image : images) {
            String filename = FileUtil.saveImageToLocal(image, uploadDir);
            fileNames.add(filename);
        }
        return fileNames;
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