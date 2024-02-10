package com.example.testprojectfornbs.service;

import com.example.testprojectfornbs.data.entity.File;
import com.example.testprojectfornbs.data.repository.FileRepository;
import com.example.testprojectfornbs.exception.CustomException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class FileService {
  private final FileRepository fileRepository;

  public FileService(FileRepository fileRepository) {
    this.fileRepository = fileRepository;
  }

  public void uploadFile(MultipartFile file) {
    File fileToSave = new File();
    String fileName = file.getOriginalFilename().replace(".mp3", "");
    String filePath = "C:\\Users\\Muratali\\Downloads\\" + file.getOriginalFilename();

    fileToSave.setFileDate(new Date());
    fileToSave.setFilePath(filePath);
    fileToSave.setFileName(fileName);
    fileRepository.save(fileToSave);
    log.info("file saving in database");
  }

  public void uploadFileDateFromName(MultipartFile file) {
    File fileToSave = new File();
    String fileName = file.getOriginalFilename().replace(".mp3", "");
    Date date = findDateFromName(fileName);
    String filePath = "C:\\Users\\Muratali\\Downloads\\" + file.getOriginalFilename();

    fileToSave.setFileDate(date);
    fileToSave.setFilePath(filePath);
    fileToSave.setFileName(fileName);
    fileRepository.save(fileToSave);
    log.info("file saving in database");
  }

  public Date findDateFromName(String fileName) {
    String year = fileName.substring(4, 8);
    String day = fileName.substring(0, 2);
    String month = fileName.substring(2, 4);
    Date date = Date.from(LocalDate.of(Integer.parseInt(year), Integer.parseInt(month),
            Integer.parseInt(day)).atStartOfDay(ZoneId.systemDefault()).toInstant());
    return date;
  }

  public File getFileInfo(String fileName) {
    Optional<File> file = fileRepository.findByFileName(fileName);
    if (file.isPresent()) {
      log.info("get file info");
      return file.get();
    }
    throw new CustomException("file does not exit", HttpStatus.NOT_FOUND);
  }

  public Resource getFileResource(String fileName) {
    StringBuilder filePath = new StringBuilder("C:\\Users\\Muratali\\Downloads\\");
    filePath.append(fileName).append(".mp3");
    java.io.File file = new java.io.File(filePath.toString());
    if (file.exists()) {
      return new FileSystemResource(file);
    } else {
      return null;
    }
  }
}
