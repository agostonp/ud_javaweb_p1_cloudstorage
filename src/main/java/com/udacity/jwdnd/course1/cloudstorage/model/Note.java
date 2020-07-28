package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
    private Integer noteId;
    private String noteTitle;
    private String noteDescription;
    private Integer userid;

    public Note(Integer noteId, String noteTitle, String noteDescription, Integer userid) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userid = userid;
    }

    public Integer getNoteId() {
        return this.noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return this.noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return this.noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
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
            " noteId='" + noteId + "'" +
            ", noteTitle='" + noteTitle + "'" +
            ", noteDescription='" + noteDescription.replace('\n', ' ') + "'" +
            ", userid='" + userid + "'" +
            "}";
    }

}