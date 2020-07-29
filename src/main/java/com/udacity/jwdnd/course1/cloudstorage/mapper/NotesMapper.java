package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;

import org.apache.ibatis.annotations.*;

@Mapper
public interface NotesMapper {

    @Select("SELECT noteId, notetitle, notedescription, userid FROM NOTES WHERE userid = #{userid}")
    public List<Note> listByUser(Integer userid);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId} AND userid = #{userid}")
    public Note getByUser(Integer noteId, Integer userid);

    @Select("SELECT COUNT(1) FROM NOTES WHERE notetitle = #{noteTitle} AND noteid <> NVL(#{noteId}, -1) AND userid = #{userid}")
    public Boolean existsByTitleWithAnotherId(String noteTitle, Integer noteId, Integer userid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{noteTitle}, #{noteDescription}, #{userid})")
    @Options(useGeneratedKeys=true, keyProperty="noteId")
    public int insert(Note note);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId} AND userid = #{userid}")
    public int updateByUser(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId} AND userid = #{userid}")
    public void deleteByUser(Integer noteId, Integer userid);
}