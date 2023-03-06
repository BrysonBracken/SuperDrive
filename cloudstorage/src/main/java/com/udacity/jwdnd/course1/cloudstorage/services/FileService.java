package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public File getFile(int fileId){
        return fileMapper.getFile(fileId);
    }

    public List<File> getAllFiles(int userId){
        return fileMapper.getAllFiles(userId);
    }

    public void saveFile (File file) throws IOException{
        fileMapper.insert(file);
    }

    public void deleteFile(int fileId){
        fileMapper.fileDelete(fileId);
    }

    public boolean nameInUse (String fileName){
        return fileMapper.getFileByName(fileName) == null;
    }
}
