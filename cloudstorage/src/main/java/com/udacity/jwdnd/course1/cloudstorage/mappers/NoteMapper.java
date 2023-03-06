package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM notes WHERE userid = #{userId}")
    List<Note> getNotes(Integer userId);

    @Select("SELECT * FROM notes WHERE notetitle = #{noteTitle}")
    Note getByTitle(String noteTitle);

    @Insert("INSERT INTO notes (notetitle, userid, notedescription) VALUES(#{noteTitle}, #{userId}, #{noteDescription})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insert(Note note);

    @Update("UPDATE notes SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}")
    void noteUpdate(Note note);

    @Delete("DELETE FROM notes WHERE noteid = #{noteId}")
    void noteDelete(int noteId);
}
