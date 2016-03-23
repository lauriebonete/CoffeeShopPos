package org.evey.controller;

import org.evey.utility.FileUtil;
import org.evey.utility.ImageUtil;
import org.pos.coffee.bean.FileDetail;
import org.pos.coffee.service.FileDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Laurie on 11/24/2015.
 */
@Controller
@RequestMapping("/file")
public class FileDetailController extends BaseCrudController<FileDetail> {

    @Value("${upload.folder}")
    private String filePath;

    @Autowired
    private FileDetailService fileDetailService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody List<FileDetail> uploadFile(MultipartHttpServletRequest request) throws IOException{

        Map<String, MultipartFile> fileMap = request.getFileMap();

        // Maintain a list to send back the files info. to the client side
        List<FileDetail> uploadedFiles = new ArrayList<FileDetail>();

        // Iterate through the map
        for (MultipartFile multipartFile : fileMap.values()) {

            String path = FileUtil.generateFilePath(multipartFile.getOriginalFilename(), filePath);
            createFileFromBytes(multipartFile, path);

            FileDetail uploadFile = new FileDetail();
            uploadFile.setFileName(multipartFile.getOriginalFilename());
            uploadFile.setFileType(multipartFile.getContentType());
            uploadFile.setFileSize(multipartFile.getSize());
            uploadFile.setFilePath(path);

            fileDetailService.save(uploadFile);
            uploadedFiles.add(uploadFile);
        }

        return uploadedFiles;
    }

    @RequestMapping(value = "/viewImage/{id}", method = RequestMethod.GET, produces = "image/png")
    public String imageViewer(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) throws IOException{
        byte[] barray;
        OutputStream outputStream = response.getOutputStream();
        try {

            FileDetail image = fileDetailService.load(id);
            File imageCreated = ImageUtil.createImageAsPng(image.getFilePath());
            barray = FileUtil.createByteFromFile(imageCreated);

            if (barray != null) {
                response.setContentType("image/png");
                response.setHeader("Cache-Control", "public");
                outputStream.write(barray);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            outputStream.flush();
            outputStream.close();
        }
        return null;
    }

    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
    public void downloadFile(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, String> contentTypes = new HashMap<>();
        contentTypes.put("pdf", "application/pdf");
        contentTypes.put("doc", "application/msword");
        contentTypes.put("docs","application/msword");
        contentTypes.put("odt", "application/vnd.oasis.opendocument.text");
        contentTypes.put("xls", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        contentTypes.put("xlsx","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        contentTypes.put("png", "image/png");
        contentTypes.put("gif", "image/gif");
        contentTypes.put("jpeg","image/jpeg");
        contentTypes.put("jpg", "image/jpeg");
        contentTypes.put("gz",  "application/x-gzip");
        contentTypes.put("zip", "application/zip");

        byte[] barray;
        OutputStream outputStream = response.getOutputStream();
        try {

            FileDetail fileDetail = fileDetailService.load(id);
            File file = FileUtil.createFile(fileDetail.getFilePath());
            barray = FileUtil.createByteFromFile(file);

            if (barray != null) {
                response.setContentType(contentTypes.get(fileDetail.getFileType()));
                response.setHeader("Content-disposition", "attachment; filename = \"" + fileDetail.getFileName() + "\"");
                outputStream.write(barray);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            outputStream.flush();
            outputStream.close();
        }

    }

    private void createFileFromBytes(MultipartFile multipartFile, String path) throws IOException{
        FileCopyUtils.copy(multipartFile.getBytes(), new FileOutputStream(path));
    }

    private String generateFilePath(String fileName){
        Date date = new Date();
        String dateFormat = new SimpleDateFormat("yyyyMMddHHmmss").format(date);

        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt(100);

        File directory = FileUtil.createDirectory(filePath);
        String subdir = (new StringBuilder())
                .append(directory.getAbsoluteFile())
                .append(File.separator)
                .append(FileUtil.createSubdirectory()).toString();
        File subDirectory = FileUtil.createDirectory(subdir);

        String path = (new StringBuilder())
                .append(subDirectory.getAbsoluteFile())
                .append(File.separator)
                .append(dateFormat+"_")
                .append(Integer.toString(random)+"_")
                .append(fileName).toString();

        return path;
    }
}
