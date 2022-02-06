package com.ikea.filehandling.controller;

import com.google.gson.Gson;
import com.ikea.filehandling.service.FileService;
import com.ikea.filehandling.service.FileStorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ExtendWith(SpringExtension.class)
public class FileUploadControllerIntegrationTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    FileStorageService fileStorageService;
    @MockBean
    FileService fileService;

    @Test
    public void testGetFiles() throws Exception {

        ArrayList<String> list = new ArrayList<String >();
        list.add("hi");list.add("hello");
        File folder = new File("/Users/ukkumary/Desktop/file_directories");
        Mockito.when(fileService.listFilesForFolder(folder, "namePattern")).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/{namePattern}", "namePattern"))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(convertToJson(list)));
        verify(fileService).listFilesForFolder(folder, "namePattern");

    }

    @Test
    public void testFrequentWords() throws Exception {

        MockMultipartFile mockFile = new MockMultipartFile("file", "hello world hello noworld".getBytes());
        when(fileStorageService.storeFile(mockFile)).thenReturn("data");

        mockMvc.perform(multipart("/api/v1/frequent_words")
                    .file(mockFile)
                    .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isOk());

        verify(fileStorageService).storeFile(mockFile);
    }

    @Test
    public void testLongestWords() throws Exception {

        MockMultipartFile mockFile = new MockMultipartFile("file", "hello world hello noworld".getBytes());
        when(fileStorageService.storeFile(mockFile)).thenReturn("data");

        mockMvc.perform(multipart("/api/v1/longest_words")
                        .file(mockFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isOk());

        verify(fileStorageService).storeFile(mockFile);

    }

    //Converts List to Json String
    private String convertToJson(List fileNames) {
        return new Gson().toJson(fileNames);
    }
}