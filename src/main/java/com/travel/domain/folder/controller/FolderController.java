package com.travel.domain.folder.controller;

import com.travel.domain.folder.dto.FolderDetailResponse;
import com.travel.domain.folder.dto.FolderListResponse;
import com.travel.domain.folder.dto.FolderRequestDto;
import com.travel.domain.folder.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{folderId}")
    public ResponseEntity<FolderDetailResponse> getFolderDetail(@PathVariable Long folderId) {
        FolderDetailResponse response = folderService.getFolderDetail(folderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<FolderListResponse>> getFolderList() {
        List<FolderListResponse> response = folderService.getFolderList();
        return ResponseEntity.ok(response);
    }
}