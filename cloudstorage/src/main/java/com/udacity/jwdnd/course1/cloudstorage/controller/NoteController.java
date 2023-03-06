package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;

    @PostMapping
    public String uploadNote(@ModelAttribute Note note, Authentication authentication, Model model){
        String name = authentication.getName();
        User user = userService.getUser(name);
        if (note.getNoteId() != null) {
            try {
                noteService.updateNote(note);
                model.addAttribute("successMessage", "Note updated successfully.");
            }catch (Exception ex){
                System.out.println(ex.getLocalizedMessage());
                model.addAttribute("errorMessage", "Note failed to update!");
            }
        }else{
            if (!noteService.titleInUse(note.getNoteTitle())) {
                System.out.println("check");
                model.addAttribute("errorMessage", "A note with that name already exists!");
            } else {
                try {
                    System.out.println("before save note");
                    note.setUserId(user.getUserId());

                    noteService.saveNote(note);
                    model.addAttribute("successMessage", "Note saved successfully.");
                    System.out.println("after save note");
                }catch (Exception ex){
                    System.out.println(ex.getLocalizedMessage());
                    model.addAttribute("errorMessage", "Note failed to save!");
                }
            }
        }
        return "result";
    }

    @GetMapping("/delete/{noteid}")
    public String deleteNote(@PathVariable int noteid, Model model) {
        try {
            noteService.deleteNote(noteid);
            model.addAttribute("successMessage", "Note successfully deleted!");
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            model.addAttribute("errorMessage", "Note could not be deleted!");
        }
        return "result";
    }
}
