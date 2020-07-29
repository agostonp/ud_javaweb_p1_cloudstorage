package com.udacity.jwdnd.course1.cloudstorage.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;

import org.springframework.stereotype.Service;

@Service
public class NotesService {
    private final NotesMapper notesMapper;
    private final UserService userService;

    public NotesService(NotesMapper notesMapper, UserService userService) {
        this.notesMapper = notesMapper;
        this.userService = userService;
    }
    
    @PostConstruct
    public void postConstruct() {
        System.out.println("NotesService bean created");
    }

    public boolean isNoteValid(Note note, String username) {

        if(note.getNoteTitle() == null || note.getNoteTitle().isBlank()) {
            return false;
        }

        int userid = userService.getUser(username).getUserid();

        return !notesMapper.existsByTitleWithAnotherId(note.getNoteTitle(), note.getNoteId(), userid);
    }

    public int saveNote(final Note note, final String username) throws IOException {
        System.out.printf("In NotesService::saveNote: %s username: \"%s\"\n", note, username);

        int userid = userService.getUser(username).getUserid();
        note.setUserid(userid);

        int noteId = 0;
        if(note.getNoteId() == null) {
            noteId = notesMapper.insert(note);
            System.out.println("Note insterted into database with noteId:" + noteId);
        }
        else {
            noteId = notesMapper.updateByUser(note);
            System.out.println("Note updated in database with noteId:" + noteId);
        }
        

        if(noteId < 1) {
            throw new IOException("Failed to insert/update Note data to database");
        }
        return noteId;
    }

    public void deleteNote(final Integer noteId, final String username) {
        System.out.printf("In NotesService::deleteNote noteId: %d username: %s\n", noteId, username );

        int userid = userService.getUser(username).getUserid();

        notesMapper.deleteByUser(noteId, userid);
    }

    public List<Note> listNotes(String userName) {
        System.out.println("In NotesService::listNotes");
        int userid = userService.getUser(userName).getUserid();
        List<Note> noteList = notesMapper.listByUser(userid);
        for(Note note : noteList) {
            System.out.printf("noteList id: %d title:%s\n", note.getNoteId(), note.getNoteTitle());
        }
        return noteList;
    }
}
