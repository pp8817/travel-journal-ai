package com.travel.global.util;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
public class FileUtil {

    @PostConstruct
    public void init() {
        File imageDir = new File("/tmp/images");
        if (!imageDir.exists()) {
            imageDir.mkdirs(); // 여기서 폴더 생성
        }
    }

    public static String saveImageToLocal(MultipartFile image, String uploadDir) {
        try {
            // 폴더 없으면 생성
            Path directoryPath = Paths.get(uploadDir);
            if (Files.notExists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // 고유한 파일 이름 생성
            String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path fullPath = directoryPath.resolve(filename);

            // 파일 저장
            image.transferTo(fullPath);
            log.info("✅ 이미지 저장 완료: {}", fullPath);
            return filename;

        } catch (IOException e) {
            log.error("❌ 이미지 저장 실패", e);
            throw new RuntimeException("이미지 저장 실패", e);
        }
    }
}
