package com.travel.domain.folder.controller;

import com.travel.domain.folder.dto.FolderRequestDto;
import com.travel.domain.folder.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/folders")
public class FolderController {

    private final FolderService folderService;

    @PostMapping
    public ResponseEntity<Void> createFolder(@RequestBody FolderRequestDto dto) {
        folderService.createFolder(dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}