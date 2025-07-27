package com.travel.domain.folder.repository;

import com.travel.domain.folder.model.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Long> {
}
