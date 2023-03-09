package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private CredentialService credentialService;

    @GetMapping
    public String homeView (@ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential, Model model, Authentication authentication){
        String name = authentication.getName();
        User user = userService.getUser(name);
        // Only allows signed-in users to access page
        if (user == null) {
            return "login";
        }else {
            // provides template with user's data
            int userId = user.getUserId();
            model.addAttribute("files", fileService.getAllFiles(userId));
            model.addAttribute("notes", noteService.getAllNotes(userId));
            model.addAttribute("credentials", credentialService.getAllCredentials(userId));
            return "home";
        }
    }
}
