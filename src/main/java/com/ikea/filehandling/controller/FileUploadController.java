package com.ikea.filehandling.controller;

import java.io.File;
import java.util.ArrayList;

import com.ikea.filehandling.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ikea.filehandling.payload.Response;
import com.ikea.filehandling.service.FileStorageService;

@RestController
public class FileUploadController {

    public FileUploadController(FileStorageService fileStorageService, FileService fileService) {
        this.fileStorageService = fileStorageService;
        this.fileService = fileService;
    }

    private FileStorageService fileStorageService;

    private FileService fileService;
    final File folder = new File("/Users/ukkumary/Desktop/file_directories");


    @GetMapping("/api/v1/{namePattern}")
    ArrayList<String> getFiles(@PathVariable  String namePattern) {
//    ArrayList<String> getFiles(@RequestHeader(name = "namePattern") String namePattern) {
        return fileService.listFilesForFolder(folder, namePattern);
    }

    /**
     * For a given input text file return the most frequent 10 words together with the number of occurrences for each word.
     *
     * @param file
     * @return Hashmap.
     */
    @PostMapping("/api/v1/frequent_words")
    public Response frequentWords(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);


        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new Response(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    /**
     * For a given input text file return for each line of the file the longest 2 words.
     * In case there are more than 2 words it will be returned the most frequent 2 words.
     *
     * @param file
     * @return Hashmap.
     */
    @PostMapping("/api/v1/longest_words")
    public Response longestWords(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new Response(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

}
