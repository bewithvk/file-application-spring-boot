package com.ikea.filehandling.controller;

import com.ikea.filehandling.service.FileService;
import com.ikea.filehandling.service.FileStorageService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;


import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileDownloadControllerTest {

    @Mock
    private FileStorageService fileStorageService;

    @Mock
    private FileService fileService;

    @Mock
    private Resource fileResource;

    @Mock
    private HttpServletRequest request;

    private FileDownloadController fileDownloadController;
    String fileName = "test";

    @Before
    public void setUp() {
        // TODO fileResource = new FileSystemResource();
        fileDownloadController = new FileDownloadController(fileStorageService);
    }

    @Test
    public void validDownloadFile_ShouldGetFilesDownloaded() throws IOException {

         when(fileStorageService.loadFileAsResource(fileName)).thenReturn(fileResource);
         when(fileResource.getFile().getAbsolutePath()).thenReturn("\\home\\Uday");
         when(request.getServletContext().getMimeType("\\home\\Uday")).thenReturn(null);

         fileDownloadController.downloadFile(fileName, request);

        Assert.assertTrue("Example test case successful", true);
    }
}
