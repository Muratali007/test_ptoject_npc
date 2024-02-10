package com.example.testprojectfornbs.data.repository;

import com.example.testprojectfornbs.data.entity.File;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileRepository extends JpaRepository<File, Long> {
  Optional<File> findByFileName(String fileName);
}
