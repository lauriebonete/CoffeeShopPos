package org.pos.coffee.controller;

import org.pos.coffee.bean.FileDetail;
import org.pos.coffee.service.FileDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Laurie on 11/24/2015.
 */
@Controller
@RequestMapping("/file")
public class FileDetailController extends BaseCrudController<FileDetail> {

    @Value("${image.folder}")
    private String filePath;

    @Autowired
    private FileDetailService fileDetailService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void uploadFile(MultipartHttpServletRequest request){
        try {
            Iterator<String> itr = request.getFileNames();

            while (itr.hasNext()) {
                String uploadedFile = itr.next();
                MultipartFile file = request.getFile(uploadedFile);
                String mimeType = file.getContentType();
                String filename = file.getOriginalFilename();
                Long fileSize = file.getSize();
                byte[] bytes = file.getBytes();

                String path = generateFilePath(filename);
                createFileFromBytes(bytes, path);

                FileDetail uploadFile = new FileDetail();
                uploadFile.setFileName(filename);
                uploadFile.setFileType(mimeType);
                uploadFile.setFileSize(fileSize);
                uploadFile.setFilePath(path);

                fileDetailService.save(uploadFile);
            }
        }
        catch (Exception e) {
        }

    }

    private void createFileFromBytes(byte[] bytes, String path) throws IOException{
        FileOutputStream fos = new FileOutputStream(path);
        fos.write(bytes);
        fos.close();
    }

    private String generateFilePath(String fileName){
        Date date = new Date();
        String dateFormat = new SimpleDateFormat("yyyyMMddHHmmss").format(date);

        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt(100);

        String path = (new StringBuilder())
                .append(filePath)
                .append(File.separator)
                .append(dateFormat+"_")
                .append(fileName+"_")
                .append(Integer.toString(random)).toString();

        return path;
    }
}
