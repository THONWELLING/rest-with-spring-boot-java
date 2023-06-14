package com.thonwelling.restwithspringbootjava.controllers;

import com.thonwelling.restwithspringbootjava.data.dto.v1.UploadFileReponseDTO;
import com.thonwelling.restwithspringbootjava.services.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

  @GetMapping("/downloadFile/{filename:.+}")
  public ResponseEntity<Resource> downloafFile(@PathVariable String filename, HttpServletRequest request) {
    logger.info("Reading A File On Disc!!");

    Resource resource = service.loadFileAsResource(filename);
    String contentType = "";
    try{
      contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
    } catch (Exception e){
      logger.info("Could Not Dtermine File Type");
    }
    if (contentType.isBlank()){
      contentType = "application/octet-stream";
    }

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + resource.getFilename() + "\"")
        .body(resource);
  }
}
