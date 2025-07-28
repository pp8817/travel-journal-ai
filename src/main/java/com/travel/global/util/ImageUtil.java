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

    public List<String> encodeImagesToBase64(List<MultipartFile> images) {
        List<String> base64Images = new ArrayList<>();
        for (MultipartFile image : images) {
            try {
                byte[] bytes = image.getBytes();
                String encoded = "data:" + image.getContentType() + ";base64," + Base64.getEncoder().encodeToString(bytes);
                base64Images.add(encoded);
            } catch (IOException e) {
                throw new RuntimeException("Base64 인코딩 실패", e);
            }
        }
        return base64Images;
    }

    public List<PinResponse> extractMetadata(List<MultipartFile> images) {
        return ExifUtil.extractImageMetadata(images).stream()
                .map(meta -> new PinResponse(meta.latitude(), meta.longitude(), meta.timestamp(), meta.fileName()))
                .toList();
    }
}