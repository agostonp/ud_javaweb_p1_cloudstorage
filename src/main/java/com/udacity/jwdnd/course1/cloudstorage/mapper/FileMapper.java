package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.model.MFile;

import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {
    
    @Results(id = "messageResultMap", value = {
        @Result(property = "fileId", column = "fileId"),
        @Result(property = "fileName", column = "filename"),
        @Result(property = "userid", column = "userid")
      })
    @Select("SELECT fileId, filename, userid FROM FILES WHERE userid = #{userid}")
    public List<MFile> listFileNames(Integer userid);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    public MFile getById(Integer fileId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId} AND userid = #{userid}")
    public MFile getByUser(Integer fileId, Integer userid);

    @Select("SELECT COUNT(1) FROM FILES WHERE fileName = #{fileName} AND userid = #{userid}")
    public Boolean checkFileExistsByName(String fileName, Integer userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userid}, #{fileData})")
    @Options(useGeneratedKeys=true, keyProperty="fileId")
    public int insert(MFile file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId} AND userid = #{userid}")
    public void deleteByUser(Integer fileId, Integer userid);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    public void delete(Integer fileId);
}