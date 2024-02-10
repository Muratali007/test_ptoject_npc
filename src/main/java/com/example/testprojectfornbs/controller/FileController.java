package com.example.testprojectfornbs.controller;

import com.example.testprojectfornbs.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FileController {
  @Autowired
  private FileService fileService;

  @PostMapping("/upload")
  public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
    fileService.uploadFile(file);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/upload/date")
  public ResponseEntity<?> uploadFileDateFromName(@RequestParam("file") MultipartFile file) {
    fileService.uploadFileDateFromName(file);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/fileInfo")
  public ResponseEntity<?> getFileInfo(@RequestParam String fileName) {
    return ResponseEntity.ok(fileService.getFileInfo(fileName));
  }

  @GetMapping("/download")
  public ResponseEntity<?> downloadFile(@RequestParam  String fileName) {
    Resource resource = fileService.getFileResource(fileName);
    if (resource != null) {
      HttpHeaders headers = new HttpHeaders();
      headers.add(HttpHeaders.CONTENT_DISPOSITION,
              "attachment; filename=\"" + resource.getFilename() + "\"");
      headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

      return ResponseEntity.ok()
              .headers(headers)
              .body(resource);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }
}
