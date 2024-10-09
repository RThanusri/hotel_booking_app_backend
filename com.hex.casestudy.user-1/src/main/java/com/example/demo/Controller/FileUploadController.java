package com.example.demo.Controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;
@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @Value("${upload.dir}")
    private String uploadDir; 

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("image") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Please select a file to upload.");
        }

        try {
            
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            
            String fileName = file.getOriginalFilename().replace(" ", "_"); // Replace spaces with underscores

            Path filePath = Paths.get(uploadDir, fileName);
            Files.copy(file.getInputStream(), filePath);
            
            System.out.println("Saving file to: " + filePath.toString());


          
            return ResponseEntity.ok("http://localhost:8080/uploads/" + fileName); // Adjust as needed
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace to the console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }
}
