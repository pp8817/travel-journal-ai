package com.travel.global.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ExifUtil {

    public record ImageMeta(Double latitude, Double longitude, LocalDateTime timestamp, String fileName) {}

    public static List<ImageMeta> extractImageMetadata(List<MultipartFile> images) {
        List<ImageMeta> metadataList = new ArrayList<>();

        for (MultipartFile image : images) {
            try (InputStream input = image.getInputStream()) {
                Metadata metadata = ImageMetadataReader.readMetadata(input);

                // GPS
                GpsDirectory gpsDir = metadata.getFirstDirectoryOfType(GpsDirectory.class);
                Double latitude = null;
                Double longitude = null;
                if (gpsDir != null && gpsDir.getGeoLocation() != null) {
                    latitude = gpsDir.getGeoLocation().getLatitude();
                    longitude = gpsDir.getGeoLocation().getLongitude();
                }

                // 촬영 시간
                ExifSubIFDDirectory exifDir = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
                LocalDateTime timestamp = null;
                if (exifDir != null) {
                    Date originalDate = exifDir.getDateOriginal();
                    if (originalDate != null) {
                        timestamp = originalDate.toInstant().atZone(TimeZone.getDefault().toZoneId()).toLocalDateTime();
                    }
                }

                metadataList.add(new ImageMeta(latitude, longitude, timestamp, image.getOriginalFilename()));
            } catch (Exception e) {
                // 로그로 남기고, null 값으로 계속
                metadataList.add(new ImageMeta(null, null, null, image.getOriginalFilename()));
            }
        }

        return metadataList;
    }
}