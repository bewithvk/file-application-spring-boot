package com.ikea.filehandling.controller;

import com.ikea.filehandling.service.FileService;
import com.ikea.filehandling.service.FileStorageService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
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
    private File file;

    @Mock
    private ServletContext servletContext;

    @Mock
    private HttpServletRequest request;

    private FileDownloadController fileDownloadController;
    String fileName = "test";

    @Before
    public void setUp() {
        fileDownloadController = new FileDownloadController(fileStorageService);
    }

    @Test
    public void validDownloadFile_ShouldGetFilesDownloaded() throws IOException {

         when(fileStorageService.loadFileAsResource(fileName)).thenReturn(fileResource);
         when(fileResource.getFile()).thenReturn(file);
         when(file.getAbsolutePath()).thenReturn("\\home\\Uday");
         when(request.getServletContext()).thenReturn(servletContext);
         when(servletContext.getMimeType("\\home\\Uday")).thenReturn("image/png");

         ResponseEntity responseEntity = fileDownloadController.downloadFile(fileName, request);

         Assert.assertEquals(200, responseEntity.getStatusCodeValue());
         Assert.assertEquals("image/png",responseEntity.getHeaders().getContentType().toString());
    }

    @Test
    public void DownloadFileWithTypeNull_ShouldGetOctetFilesDownloaded() throws IOException {

        when(fileStorageService.loadFileAsResource(fileName)).thenReturn(fileResource);
        when(fileResource.getFile()).thenReturn(file);
        when(file.getAbsolutePath()).thenReturn("\\home\\Uday");
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletContext.getMimeType("\\home\\Uday")).thenReturn(null);

        ResponseEntity responseEntity = fileDownloadController.downloadFile(fileName, request);

        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals("application/octet-stream",responseEntity.getHeaders().getContentType().toString());
    }
}
