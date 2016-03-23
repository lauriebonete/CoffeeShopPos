package org.evey.utility;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Laurie on 11/27/2015.
 */
public class FileUtil {

    public static File createDirectory(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        return file;
    }

    public static String createSubdirectory() {
        String dateNow = DateUtil.convertShortDate(new Date());
        String subDir = dateNow.substring(0, 4) + File.separator + dateNow.substring(4,6) + File.separator + dateNow.substring(6);
        return subDir;
    }

    public static File createFile(String path){
        File file = new File(path);
        return file;
    }

    public static byte[] createByteFromFile(File file){
        try {
            InputStream is = FileUtil.getFileStream(file.getAbsolutePath());
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();

            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) > 0) {
                outStream.write(buffer, 0, len);
            }
            return outStream.toByteArray();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream getFileStream(String name) throws FileNotFoundException {
        File file = new File(name);
        if (file.isAbsolute()) {
            return new FileInputStream(file);
        } else {
            // check relative to source
            if (file.exists()) {
                return new FileInputStream(file);
            } else {
                return FileUtil.class.getClassLoader().getResourceAsStream(name);
            }
        }
    }

    public static String generateFilePath(String fileName, String filePath){
        Date date = new Date();
        String dateFormat = new SimpleDateFormat("yyyyMMddHHmmss").format(date);

        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt(100);

        File directory = createDirectory(filePath);
        String subdir = (new StringBuilder())
                .append(directory.getAbsoluteFile())
                .append(File.separator)
                .append(createSubdirectory()).toString();
        File subDirectory = createDirectory(subdir);

        String path = (new StringBuilder())
                .append(subDirectory.getAbsoluteFile())
                .append(File.separator)
                .append(dateFormat+"_")
                .append(Integer.toString(random)+"_")
                .append(fileName).toString();

        return path;
    }
}
