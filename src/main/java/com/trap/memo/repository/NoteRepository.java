package com.trap.memo.repository;

import com.trap.memo.model.Note;

import java.util.List;

public interface NoteRepository {
    void save(Note note);
    Note getOne(Long id);
    List<Note> findAll();
    List<Note> findAllByUserId(Long id);
    void delete(Long id);
}
