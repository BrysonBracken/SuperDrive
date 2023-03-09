package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

// Controller used for any interaction with files from the home page
@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    // Mapping for initial upload of a file
    @PostMapping
    public String uploadFile(Authentication authentication, MultipartFile fileUpload, Model model) throws IOException {
        // Checks if a file was selected
        if (fileUpload.isEmpty()) {
            model.addAttribute("errorMessage", "Please select a file to upload!");
            return "result";
        }
        // Checks if file size is within limit
        if (fileUpload.getSize() > 210000) {
            model.addAttribute("errorMessage", "Please select a file under 2KB to upload!");
            return "result";
        }
        // Checks if a file already exist with provided file name
        if (!fileService.nameInUse(fileUpload.getOriginalFilename())) {
            model.addAttribute("errorMessage", "A file with that name already exists!");
        } else {
            // Attempts to save file
            String name = authentication.getName();
            User user = userService.getUser(name);
            File newFile = new File();

            newFile.setFileName(fileUpload.getOriginalFilename());
            newFile.setData(fileUpload.getBytes());
            newFile.setType(fileUpload.getContentType());
            newFile.setUserId(user.getUserId());

            fileService.saveFile(newFile);
            model.addAttribute("successMessage", "File saved successfully.");
        }
        return "result";
    }

    // Mapping for downloading of a file
    @GetMapping("/download/{fileid}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable int fileid){

        try {
            File file = fileService.getFile(fileid);
            byte[] content = file.getData();
            String name = file.getFileName();

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + name + "\"")
                    .body(content);
        } catch (Exception ex){
            System.out.println(ex.getLocalizedMessage());
        }
        return null;
    }

    // Mapping for deletion of a file
    @GetMapping("/delete/{fileid}")
    public String deleteFile(@PathVariable int fileid, Model model){
        //Attempts to delete file and provide a result message on the result page
        try {
            fileService.deleteFile(fileid);
            model.addAttribute("successMessage", "File successfully deleted!");
        } catch (Exception ex){
            System.out.println(ex.getLocalizedMessage());
            model.addAttribute("errorMessage", "File could not be deleted!");
        }
        return "result";
    }
}
