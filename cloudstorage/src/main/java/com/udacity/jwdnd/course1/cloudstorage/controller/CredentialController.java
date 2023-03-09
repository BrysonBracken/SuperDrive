package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Controller used for any interaction with credentials from the home page
@Controller
@RequestMapping("/credential")
public class CredentialController {

    @Autowired
    private CredentialService credentialService;
    @Autowired
    private UserService userService;

    // Mapping for initial credential creation and updates to a credential
    @PostMapping
    public String uploadCredential(@ModelAttribute Credential credential, Authentication authentication, Model model) {
        String name = authentication.getName();
        User user = userService.getUser(name);
        // Check whether is an update or new credential
        if (credential.getCredentialId() != null) {
            // Attempts to update and provides a result message on the result page
            try {
                credentialService.updateCredential(credential);
                model.addAttribute("successMessage", "Credential updated successfully.");
            } catch (Exception ex) {
                System.out.println(ex.getLocalizedMessage());
                model.addAttribute("errorMessage", "Credential failed to update!");
            }
        } else {
            if (!credentialService.urlInUse(credential.getUrl())) {
                model.addAttribute("errorMessage", "A note with that name already exists!");
            } else {
                // Attempts to save cred and provide a result message on the result page
                try {
                    credential.setUserId(user.getUserId());
                    credential.setUsername(credential.getUsername());

                    credentialService.saveCredential(credential);
                    model.addAttribute("successMessage", "Credential saved successfully.");
                } catch (Exception ex) {
                    System.out.println(ex.getLocalizedMessage());
                    model.addAttribute("errorMessage", "Credential failed to save!");
                }
            }
        }
        return "result";
    }

    // Handles the deletion of a credential
    @GetMapping("/delete/{credentialid}")
    public String deleteCredential(@PathVariable int credentialid, Model model) {
        //Attempts to delete cred and provide a result message on the result page
        try {
            credentialService.deleteCredential(credentialid);
            model.addAttribute("successMessage", "Credential successfully deleted!");
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            model.addAttribute("errorMessage", "Credential could not be deleted!");
        }
        return "result";
    }
}
