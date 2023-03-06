package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM credentials WHERE userid = #{userId}")
    List<Credential> getCredentials(int userId);

    @Select("SELECT * FROM credentials WHERE url = #{url}")
    Credential getByUrl(String url);

    @Insert("INSERT INTO credentials (userid, url, username, credentialpassword, encryptionkey) VALUES(#{userId}, #{url}, #{username}, #{credentialPassword}, #{encryptionKey})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer insert(Credential credential);

    @Update("UPDATE credentials SET url = #{url}, username = #{username}, credentialpassword = #{credentialPassword}, encryptionkey = #{encryptionKey} WHERE credentialid = #{credentialId}")
    void credentialUpdate(Credential credential);

    @Delete("DELETE FROM credentials WHERE credentialid = #{credentialId}")
    void credentialDelete(int credentialId);
}
