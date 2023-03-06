package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface FileMapper {
    @Select("SELECT * FROM files WHERE fileid = #{fileId}")
    File getFile(int fileId);

    @Select("SELECT * FROM files WHERE filename = #{fileName}")
    File getFileByName(String fileName);

    @Select("SELECT * FROM files WHERE userid = #{userId}")
    List<File> getAllFiles(int userId);

    @Insert("INSERT INTO files (userid, filename, type, data) VALUES(#{userId}, #{fileName}, #{type}, #{data})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Delete("DELETE FROM files WHERE fileid = #{fileId}")
    void fileDelete(int fileId);
}
