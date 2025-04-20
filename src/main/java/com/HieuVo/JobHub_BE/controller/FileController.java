package com.HieuVo.JobHub_BE.controller;

import com.HieuVo.JobHub_BE.Service.FileService;
import com.HieuVo.JobHub_BE.Util.Anotation.ApiMessage;
import com.HieuVo.JobHub_BE.Util.Error.StorageException;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1")
public class FileController {

    @Value("${jobhub.upload-file.base-uri}")
    private String baseURI;

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/files")
    public String upload(
            @RequestParam("file") MultipartFile file, @RequestParam("folder") String folder) throws URISyntaxException, IOException {
//validate file

//        create a directory if not exist
        this.fileService.createDirectory(baseURI + folder);

        this.fileService.saveFile(file, folder);
        return file.getResource().getFilename();

    }


    @GetMapping("/files")
    @ApiMessage("download file")
    public ResponseEntity<Resource> downloadFile(@RequestParam(name = "fileName", required = false) String fileName,
                                                 @RequestParam(name = "folder", required = false) String folder)
            throws FileNotFoundException, URISyntaxException, StorageException {
        if (fileName == null || folder == null) {
            throw new StorageException ("Missing required params (File or folder is not exist!)");
        }

        long fileLength = this.fileService.getFileLength(fileName, folder);
        if (fileLength == 0) {
            throw new StorageException("File with name " + fileName + " not found");
        }
        InputStreamResource resource = this.fileService.getResource(fileName, folder);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentLength(fileLength)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
