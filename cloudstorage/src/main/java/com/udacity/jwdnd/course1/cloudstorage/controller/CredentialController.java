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

@Controller
@RequestMapping("/credential")
public class CredentialController {

    @Autowired
    private CredentialService credentialService;
    @Autowired
    private UserService userService;

    @PostMapping
    public String uploadCredential(@ModelAttribute Credential credential, Authentication authentication, Model model) {
        String name = authentication.getName();
        User user = userService.getUser(name);
        if (credential.getCredentialId() != null) {
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

    @GetMapping("/delete/{credentialid}")
    public String deleteCredential(@PathVariable int credentialid, Model model) {
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
