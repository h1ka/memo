package com.trap.memo.mapper;

import com.trap.memo.model.Note;
import com.trap.memo.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteMapper implements RowMapper<Note> {
    @Override
    public Note mapRow(ResultSet resultSet, int i) throws SQLException {
        Note note = new Note();
        User user = new User();
        note.setId(resultSet.getLong("id"));
        note.setName(resultSet.getString("name"));
        note.setBody(resultSet.getString("body"));
        note.setCreateDate(resultSet.getDate("create_date"));
        Blob blob = resultSet.getBlob("identicon");
        int blobLength = (int) blob.length();
        byte[] blobAsBytes = blob.getBytes(1, blobLength);
        blob.free();
        note.setPhoto(blobAsBytes);
        user.setId(resultSet.getLong("user_id"));
        user.setUsername(resultSet.getString("username"));
        note.setUser(user);
        return note;
    }
}
