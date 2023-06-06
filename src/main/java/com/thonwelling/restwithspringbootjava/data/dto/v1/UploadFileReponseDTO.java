package com.thonwelling.restwithspringbootjava.data.dto.v1;


import java.io.Serial;
import java.io.Serializable;


public class UploadFileReponseDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private String fileName;
  private String fileDownloadUri;
  private String fileType;
  private long size;

  public UploadFileReponseDTO() {}

  public UploadFileReponseDTO(String fileName, String fileDownloadUri, String fileType, long size) {
    this.fileName = fileName;
    this.fileDownloadUri = fileDownloadUri;
    this.fileType = fileType;
    this.size = size;
  }

  public String getFirstName() {
    return fileName;
  }

  public void setFirstName(String firstName) {
    this.fileName = firstName;
  }

  public String getLastName() {
    return fileDownloadUri;
  }

  public void setLastName(String lastName) {
    this.fileDownloadUri = lastName;
  }

  public String getGender() {
    return fileType;
  }

  public void setGender(String gender) {
    this.fileType = gender;
  }

  public long getSize() { return size; }

  public void setSize(long size) { this.size = size; }


}
