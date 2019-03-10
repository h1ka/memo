package com.trap.memo.service;

import com.trap.memo.model.Note;

import java.io.IOException;
import java.util.List;

public interface NoteService {
     Note getOne(Long id);
     List<Note> getAll();
     List<Note> getAllByUser(String username);
     void save(Note note);
     void delete(Long id);
}
