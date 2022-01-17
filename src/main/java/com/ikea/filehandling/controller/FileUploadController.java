package com.ikea.filehandling.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ikea.filehandling.FileUtil;
import com.ikea.filehandling.FileUtilLongest;
import com.ikea.filehandling.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ikea.filehandling.payload.Response;
import com.ikea.filehandling.service.FileStorageService;

@RestController
public class FileUploadController {


    private FileStorageService fileStorageService;
    FileService fileService;
    final File folder = new File("/Users/ukkumary/Desktop/file_directories");

    @Autowired
    public FileUploadController(FileStorageService fileStorageService, FileService fileService) {
        this.fileStorageService = fileStorageService;
        this.fileService = fileService;
    }

    /**
     * Fetch files form folder as per given reg-ex.
     * Return all files inside a directory located on server side. The files name should match a regular expression given
     * as input for the endpoint. Directory name and path is defined on the server side.
     *
     * @return ArrayList.
     */
    @GetMapping("/api/v1/{namePattern}")
    ArrayList<String> getFiles(@PathVariable  String namePattern) {
        return fileService.listFilesForFolder(folder, namePattern);
    }

    
    /**
     * For a given input text file return the most frequent 10 words together with the number of occurrences for each word.
     *
     * @return Json.
     */
    @PostMapping("/api/v1/frequent_words")
    public Response frequentWords(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String fileName = fileStorageService.storeFile(multipartFile);

        File file = File.createTempFile(fileName, ".txt");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();

        FileUtil fileUtil = new FileUtil();
        LineNumberReader lineNumberReader = null;

        lineNumberReader = new LineNumberReader(new FileReader(file));
        String st;
        String content = "";
        while ((st = lineNumberReader.readLine()) != null) {
            if (!st.isEmpty()) {
                content = content + st;
            }
        }
        Map<String, Integer> wordList = fileUtil.characterCount(content);
        return new Response(wordList);
    }

    /**
     * For a given input text file return for each line of the file the longest 2 words.
     * In case there are more than 2 words it will be returned the most frequent 2 words.
     *
     *
     * @return Hashmap.
     */
    @PostMapping("/api/v1/longest_words")
    public Response longestWords(@RequestParam("file") MultipartFile file1) throws IOException {
        String fileName = fileStorageService.storeFile(file1);

        File file = new File("uploads/"+fileName);
        FileUtil fileUtil = new FileUtil();

        LineNumberReader lineNumberReader = null;
        lineNumberReader = new LineNumberReader(new FileReader(file));
        String st;
        String content = "";

        Map<String, Integer> longWords = new HashMap<>();
        while ((st = lineNumberReader.readLine()) != null) {
            if (!st.isEmpty()) {
                FileUtilLongest wordList = fileUtil.longestWords(st);
                longWords.put(wordList.getFirst(), lineNumberReader.getLineNumber());
                longWords.put(wordList.getSecond(), lineNumberReader.getLineNumber());
                content = content + st;
            }
        }
        return new Response(longWords);
    }
}
