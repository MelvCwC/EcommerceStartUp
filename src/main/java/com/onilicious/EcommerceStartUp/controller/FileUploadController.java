package com.onilicious.EcommerceStartUp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/uploads")
public class FileUploadController {

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file) {
        if(file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }

        try {
            //Create directory if directiory does not exist
            File directory = new File(UPLOAD_DIR);
            if(!directory.exists()) {
                directory.mkdirs();
            }

            //Generate unique filename so users don't overwrite each other data
            String originalFileName = file.getOriginalFilename();
            String fileExtension = "";

            if(originalFileName != null && originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            //Save the files physically to the disk folder
            Path path = Paths.get(UPLOAD_DIR, uniqueFileName);
            file.transferTo(path.toFile());

            //Return live url path string back to react
            String fileUrl = "http://localhost:8080/uploads/" + uniqueFileName;
            return ResponseEntity.ok(fileUrl);
        } catch(IOException e) {
            e.printStackTrace();;
            return ResponseEntity.internalServerError().body("Failed to upload image due to server error");
        }
    }

}
