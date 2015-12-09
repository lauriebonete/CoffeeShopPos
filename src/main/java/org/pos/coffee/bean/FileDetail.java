package org.pos.coffee.bean;

import org.evey.bean.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Laurie on 11/23/2015.
 */
@Entity
@Table(name = "FILE_DETAIL")
public class FileDetail extends BaseEntity {
    private String fileName;
    private String filePath;
    private Long fileSize;
    private String fileType;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
