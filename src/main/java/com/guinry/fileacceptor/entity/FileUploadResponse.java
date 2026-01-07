package com.guinry.fileacceptor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponse {

    private String projectName;
    private String originalName;
    private String storedName;
    private String fullPath;
    private long size;
}
