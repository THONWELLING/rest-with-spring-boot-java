package com.thonwelling.restwithspringbootjava.controllers;

import com.thonwelling.restwithspringbootjava.data.dto.v1.UploadFileReponseDTO;
import com.thonwelling.restwithspringbootjava.services.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/file/v1")
@Tag(name = "File Endpoint")
public class FileContoller {
  private final Logger logger = Logger.getLogger(FileContoller.class.getName());

  @Autowired
  private FileStorageService service;

  @PostMapping("/uploadFile")
  public UploadFileReponseDTO uploadFile(@RequestParam("file")MultipartFile file) {
    logger.info("Storing File To Disc!!");
    var fileName = service.storeFile(file);
    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/api/file/v1/downloadFile/").path(fileName).toUriString();
    return new UploadFileReponseDTO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
  }

@PostMapping("/uploadMultipleFiles")
  public List<UploadFileReponseDTO> uploadMultipleFiles(@RequestParam("files")MultipartFile[] files) {
    logger.info("Storing Files To Disc!!");
  return Arrays.stream(files).map(this::uploadFile).collect(Collectors.toList());
  }
}
