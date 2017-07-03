package com.dyndyn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Entity
@Table(name = "image")
public class Image implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "content_type")
    private String contentType;

    @Transient
    private MultipartFile multipartFile;

    @Transient
    private String data;

    @Column(name = "data")
    private byte[] imageData;


    public Image() {

    }

    public Image(int id, String fileName, String contentType, MultipartFile multipartFile, byte[] imageData) {
        this.id = id;
        this.fileName = fileName;
        this.contentType = contentType;
        this.multipartFile = multipartFile;
        this.imageData = imageData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @JsonIgnore
    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @PrePersist
    public void onCreate() throws IOException {
        if(imageData == null && multipartFile != null){

            imageData = multipartFile.getBytes();
            fileName = multipartFile.getOriginalFilename();
            if(fileName.length() > 64){
                fileName = fileName.substring(0, 64);
            }
            contentType = multipartFile.getContentType();

        }
    }

    public void encodeBase64() throws IOException {
        onCreate();
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodeBase64 = encoder.encode(imageData);
        StringBuilder sb = new StringBuilder("data:");
        sb.append(contentType).append(";base64,");
        sb.append(new String(encodeBase64, "UTF-8"));
        data = sb.toString();
    }
}
