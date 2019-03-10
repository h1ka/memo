package com.trap.memo.service;

import com.trap.memo.model.Note;
import com.trap.memo.model.User;
import com.trap.memo.repository.NoteRepository;
import com.trap.memo.repository.UserRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.in;


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
