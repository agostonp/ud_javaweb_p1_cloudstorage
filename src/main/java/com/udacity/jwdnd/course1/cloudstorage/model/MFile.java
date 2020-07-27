package com.udacity.jwdnd.course1.cloudstorage.model;

public class MFile {
    private Integer fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private Integer userid;

    public MFile(Integer fileId, String fileName, Integer userid) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = null;
        this.fileSize = null;
        this.userid = userid;
    }

    public MFile(Integer fileId, String fileName, String contentType, String fileSize, Integer userid) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userid = userid;
    }

    public Integer getFileId() {
        return this.fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getUserid() {
        return this.userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "{" +
            " fileId='" + fileId + "'" +
            ", fileName='" + fileName + "'" +
            ", contentType='" + contentType + "'" +
            ", fileSize='" + fileSize + "'" +
            ", userid='" + userid + "'" +
            "}";
    }
}
