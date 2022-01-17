package com.ikea.filehandling.controller;

import com.ikea.filehandling.payload.Response;
import com.ikea.filehandling.service.FileService;
import com.ikea.filehandling.service.FileStorageService;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileUploadControllerTest {

    @Mock
    private FileStorageService fileStorageService;

    @Mock
    private FileService fileService;

    @Mock
    private Resource fileResource;

    @Mock
    private File file;

    @Mock
    private ServletContext servletContext;

    @Mock
    private HttpServletRequest request;

    private FileUploadController fileUploadController;
    String fileName = "test";
    final File folder = new File("/Users/ukkumary/Desktop/file_directories");

    @Before
    public void setUp() {
        fileUploadController = new FileUploadController(fileStorageService, fileService);
    }

    @Test
    public void validDownloadFile_ShouldGetFilesDownloaded() throws IOException {

        ArrayList<String>fileList = new ArrayList<String>();
        fileList.add("abc");
        fileList.add("xyz");
        when(fileService.listFilesForFolder(folder, "xyz")).thenReturn(fileList);
        List<String> response = fileUploadController.getFiles("xyz");

        Assert.assertEquals(fileList.get(1), response.get(1));
    }

    @Test
    public void frequentWordsTest() throws IOException {

        InputStream uploadStream = FileUploadController.class.getClassLoader().getResourceAsStream("my-file.txt");
        MockMultipartFile file = new MockMultipartFile("my-file.txt", uploadStream);
        when(fileStorageService.storeFile(file)).thenReturn("my-file.txt");
        Response response = fileUploadController.frequentWords(file);

        Assert.assertEquals("Friends", response.getRes().get(0));
    }
}
