package com.trap.memo.service;

import com.trap.memo.model.Note;
import com.trap.memo.model.User;
import com.trap.memo.repository.NoteRepository;
import com.trap.memo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;



@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public Note getOne(Long id) {
        Note note = noteRepository.getOne(id);
        return note;
    }

    @Override
    public List<Note> getAll() {
        List<Note> notes = noteRepository.findAll();
        return notes;
    }
    public List<Note> getAllByUser(String username){
        User user = userRepository.findByUsername(username);
        Long id = user.getId();
        List<Note> userNotes = noteRepository.findAllByUserId(id);
        return userNotes;
    }

    @Override
    public void save(Note note) {
         noteRepository.save(note);
    }

    @Override
    public void delete(Long id) {
        noteRepository.delete(id);
    }

}
