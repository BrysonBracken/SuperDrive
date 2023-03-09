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

// Controller used for any interaction with notes from the home page
@Controller
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;

    // Mapping for initial note creation and updates to a note
    @PostMapping
    public String uploadNote(@ModelAttribute Note note, Authentication authentication, Model model){
        String name = authentication.getName();
        User user = userService.getUser(name);
        // Check whether is an update or new note
        if (note.getNoteId() != null) {
            // Attempts to update and provides a result message on the result page
            try {
                noteService.updateNote(note);
                model.addAttribute("successMessage", "Note updated successfully.");
            }catch (Exception ex){
                System.out.println(ex.getLocalizedMessage());
                model.addAttribute("errorMessage", "Note failed to update!");
            }
        }else{
            // Prevents two notes with the same name to be saved
            if (!noteService.titleInUse(note.getNoteTitle())) {
                model.addAttribute("errorMessage", "A note with that name already exists!");
            } else {
                // Attempts to save note and provide a result message on the result page
                try {
                    note.setUserId(user.getUserId());

                    noteService.saveNote(note);
                    model.addAttribute("successMessage", "Note saved successfully.");
                }catch (Exception ex){
                    System.out.println(ex.getLocalizedMessage());
                    model.addAttribute("errorMessage", "Note failed to save!");
                }
            }
        }
        return "result";
    }

    // Handles the deletion of a note
    @GetMapping("/delete/{noteid}")
    public String deleteNote(@PathVariable int noteid, Model model) {
        //Attempts to delete note and provide a result message on the result page
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
