package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;

@RestController
@RequestMapping("/profile")
@CrossOrigin
public class ProfileController {

    private UserDao userDao;
    private ProfileDao profileDao;

    public ProfileController(ProfileDao profileDao, UserDao userDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }

    // GET http://localhost:8080/profile
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public Profile getProfile(Authentication authentication) {
        try {
            String username = authentication.getName();
            int userId = userDao.getIdByUsername(username);
            return profileDao.getByUserId(userId);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "unable to retrieve profile"
            );

        }

    }
    // PUT http://localhost:8080/profile
    @PreAuthorize("isAuthenticated()")
    @PutMapping
    public void updateProfile(
            Authentication authentication,
            @RequestBody Profile profile)
    {
        try {
            String username = authentication.getName();
            int userId = userDao.getIdByUsername(username);
            profileDao.update(userId, profile);

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unable to retrieve profile");
        }
    }


}
