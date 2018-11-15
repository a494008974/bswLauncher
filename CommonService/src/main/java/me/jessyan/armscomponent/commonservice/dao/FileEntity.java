package me.jessyan.armscomponent.commonservice.dao;

import com.google.gson.annotations.Expose;

/**
 * Created by zhou on 2018/11/14.
 */

public class FileEntity {
    @Expose private String filename;
    @Expose private String filepath;
    @Expose private String filesize;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }
}
