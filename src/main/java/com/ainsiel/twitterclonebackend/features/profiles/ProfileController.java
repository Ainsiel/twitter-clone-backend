package com.ainsiel.twitterclonebackend.features.profiles;


import com.ainsiel.twitterclonebackend.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final ProfileService profileService;


    @GetMapping("/{username}")
    public ResponseEntity<ProfileResponse> getProfileByUsername(
            @PathVariable String username,
            @RequestHeader("Authorization") String authHeader){

        return ResponseEntity.ok(profileService.getProfileByUsername(
                username,
                jwtService.getUsernameFromRequestHeader(authHeader)
        ));
    }

    @GetMapping("/user")
    public ResponseEntity<UserProfileResponse> getUserProfile(
            @RequestHeader("Authorization") String authHeader){

        return ResponseEntity.ok(profileService.getUserProfileByUsername(
                jwtService.getUsernameFromRequestHeader(authHeader)
        ));
    }

    @PutMapping("/user")
    public ResponseEntity<UserProfileResponse> modifyUserProfile(
            @RequestBody ProfileRequest profileRequest,
            @RequestHeader("Authorization") String authHeader){

        return ResponseEntity.ok(profileService.modifyUserProfileByUsername(
                profileRequest,
                jwtService.getUsernameFromRequestHeader(authHeader)
        ));
    }
}
