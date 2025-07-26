package com.travel.domain.diary.service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.lang.GeoLocation;
import com.travel.domain.diary.dto.response.PinResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class PhotoMetadataService {

    public List<PinResponse> extractPins(List<MultipartFile> images) {
        List<PinResponse> pins = new ArrayList<>();

        for (MultipartFile file : images) {
            try {
                Metadata metadata = ImageMetadataReader.readMetadata(file.getInputStream());

                GpsDirectory gps = metadata.getFirstDirectoryOfType(GpsDirectory.class);
                ExifSubIFDDirectory exif = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

                if (gps != null && exif != null) {
                    GeoLocation location = gps.getGeoLocation();
                    Date date = exif.getDateOriginal();

                    if (location != null && date != null) {
                        PinResponse pin = new PinResponse(
                                location.getLatitude(),
                                location.getLongitude(),
                                date.toInstant().toString(),
                                file.getOriginalFilename()
                        );
                        pins.add(pin);
                    }
                }
            } catch (Exception e) {
                // 로그로만 처리 (유효하지 않은 EXIF가 있을 수 있음)
                System.err.println("메타데이터 파싱 실패: " + file.getOriginalFilename());
            }
        }

        return pins;
    }
}