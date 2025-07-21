package com.travel.domain.folder.service;

import com.travel.domain.folder.dto.FolderRequestDto;
import com.travel.domain.folder.model.Folder;
import com.travel.domain.folder.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    @Transactional
    public void createFolder(FolderRequestDto dto) {
        Folder folder = Folder.builder()
                .title(dto.getTitle())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();

        folderRepository.save(folder);
    }
}