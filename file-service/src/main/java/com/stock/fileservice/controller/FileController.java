package com.stock.fileservice.controller;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private Storage storage;

    @Value("${gcp.bucket.name}")
    private String bucketName;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();
            String extension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            
            // Generate a unique filename
            String fileName = UUID.randomUUID().toString() + extension;

            // Upload to Google Cloud Storage
            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName).build();
            Blob blob = storage.create(blobInfo, file.getBytes());

            // Build response with the public URL assuming the bucket objects are public readable,
            // or return the direct storage API URL (we return the storage.googleapis.com standard URL)
            String publicUrl = "https://storage.googleapis.com/" + bucketName + "/" + fileName;

            Map<String, String> response = new HashMap<>();
            response.put("fileName", fileName);
            response.put("originalFileName", originalFileName);
            response.put("publicUrl", publicUrl);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to upload file: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles() {
        List<String> fileNames = new ArrayList<>();
        
        // List all blobs in the bucket
        for (Blob blob : storage.list(bucketName).iterateAll()) {
            fileNames.add(blob.getName());
        }
        
        return new ResponseEntity<>(fileNames, HttpStatus.OK);
    }
}
