package com.thonwelling.restwithspringbootjava.services;

import com.thonwelling.restwithspringbootjava.config.FileStorageConfig;
import com.thonwelling.restwithspringbootjava.exceptions.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileStorageService {

  private final Path fileStorgeLocation;

  @Autowired
  public FileStorageService(FileStorageConfig fileStorageConfig){
    this.fileStorgeLocation = Paths.get(fileStorageConfig.getUploadDir())
        .toAbsolutePath().normalize();
    try{
      Files.createDirectories(this.fileStorgeLocation);
    }catch(Exception e){
      throw new FileStorageException("Could Not Create Directory Where Uploadede Files Will Be Storade", e);
    }
  }

  public String storeFile(MultipartFile file) {
    String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    try{
      if(filename.contains("..")) {
        throw new FileStorageException("Sorry! Filename Contains Invalid Path Sequence " + filename );
      }
      Path targetLocation = this.fileStorgeLocation.resolve(filename);
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
      return filename;
    }catch(Exception e){
      throw new FileStorageException("Could Not Store File " + filename + " Please, Try Again", e);
    }
  }

}
