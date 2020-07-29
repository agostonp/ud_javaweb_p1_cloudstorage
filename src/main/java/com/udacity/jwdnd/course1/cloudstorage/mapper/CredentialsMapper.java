package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

import org.apache.ibatis.annotations.*;

@Mapper
public interface CredentialsMapper {

    @Select("SELECT credentialid, url, username, key, password, userid FROM CREDENTIALS WHERE userid = #{userid}")
    public List<Credential> listByUser(Integer userid);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId} AND userid = #{userid}")
    public Credential getByUser(Integer credentialId, Integer userid);

    @Select("SELECT COUNT(1) FROM CREDENTIALS WHERE url = #{url} AND username = #{username} AND credentialid <> NVL(#{credentialId}, -1) AND userid = #{userid}")
    public Boolean existsByUrlUserWithAnotherId(String url, String username, Integer credentialId, Integer userid);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys=true, keyProperty="credentialId")
    public int insert(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password} WHERE credentialid = #{credentialId} AND userid = #{userid}")
    public int updateByUser(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId} AND userid = #{userid}")
    public void deleteByUser(Integer credentialId, Integer userid);
}