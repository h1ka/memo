package com.trap.memo.repository;

import com.trap.memo.mapper.NoteMapper;
import com.trap.memo.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JDBCNoteRepository implements NoteRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void save(Note note) {
        String sqlCount = "SELECT max(id) FROM notes";
        String sql = "INSERT INTO notes (name, body, create_date,identicon) VALUES (?, ?, ?, ? )";
        String sqlUserNotes = "INSERT INTO user_notes (user_id, note_id) VALUES (?, ?)";
        Long userId = note.getUser().getId();
        Blob blob = null;
        try {
             blob= new javax.sql.rowset.serial.SerialBlob(note.getPhoto());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Integer count = jdbcTemplate.queryForObject(sqlCount, Integer.class);
        Long noteId = count+1L;
        jdbcTemplate.update(sql,note.getName(),note.getBody(),note.getCreateDate(),blob);
        jdbcTemplate.update(sqlUserNotes,userId,noteId);
    }

    @Override
    @Transactional(readOnly = true)
    public Note getOne(Long id) {
        String sql = "select  notes.id,notes.name,notes.body,notes.create_date," +
                "users.id as user_id,users.username,notes.identicon from notes  left join user_notes " +
                "on notes.id=user_notes.note_id left join users " +
                "on user_notes.user_id = users.id where notes.id = ?";
        Note note = jdbcTemplate.queryForObject(sql, new NoteMapper(), id);
        return note;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Note> findAll() {
        String sql = "SELECT * from notes";
        List<Note> notes  = jdbcTemplate.query(sql,
                new BeanPropertyRowMapper(Note.class));
        return notes;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Note> findAllByUserId(Long id) {
        String sql = "select  notes.id,notes.name,notes.body,notes.create_date," +
                "users.id as user_id,users.username,notes.identicon from notes  left join user_notes " +
                "on notes.id=user_notes.note_id left join users " +
                "on user_notes.user_id = users.id where users.id = ?";
        List<Note> notes = jdbcTemplate.query(sql, new NoteMapper(), id);

        return notes;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        String deleteNote = "delete from notes where id = ?";
        String deleteUserNotes = "delete from user_notes where note_id = ?";
        jdbcTemplate.update(deleteUserNotes,id);
        jdbcTemplate.update(deleteNote, id);
    }
}
