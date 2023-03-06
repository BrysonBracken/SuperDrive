package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public void saveNote(Note note){
        System.out.println("save");
        System.out.println(note.getNoteTitle());
        System.out.println(note.getNoteTitle().getClass());
        noteMapper.insert(note);
    }

    public List<Note> getAllNotes(Integer userId){
        System.out.println("get");
        return noteMapper.getNotes(userId);
    }

    public void updateNote(Note note){
        noteMapper.noteUpdate(note);
    }

    public void deleteNote(int noteId){
        noteMapper.noteDelete(noteId);
    }

    public boolean titleInUse (String noteTitle){
        return noteMapper.getByTitle(noteTitle) == null;
    }
}
