package com.ainsiel.twitterclonebackend.features.follows;


import com.ainsiel.twitterclonebackend.features.profiles.ProfileResponse;
import com.ainsiel.twitterclonebackend.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController {

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final FollowService followService;


    @GetMapping("/{username}/follows")
    public ResponseEntity<List<ProfileResponse>> getFollowsByUsername(
           @PathVariable String username,
           @RequestHeader("Authorization") String authHeader){

        return ResponseEntity.ok(followService
                .getFollowsByUsername(
                        username,
                        jwtService.getUsernameFromRequestHeader(authHeader)
                )
        );
    }

    @GetMapping("/{username}/followers")
    public ResponseEntity<List<ProfileResponse>> getFollowersByUsername(
            @PathVariable String username,
            @RequestHeader("Authorization") String authHeader){

        return ResponseEntity.ok(followService
                .getFollowersByUsername(
                        username,
                        jwtService.getUsernameFromRequestHeader(authHeader)
                )
        );
    }

    @PostMapping("/{username}")
    public ResponseEntity<ProfileResponse> followProfileByUsername(
            @PathVariable String username,
            @RequestHeader("Authorization") String authHeader){

        return ResponseEntity.ok(followService
                .followProfileByUsername(
                        username,
                        jwtService.getUsernameFromRequestHeader(authHeader)
                )
        );
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<ProfileResponse> unfollowProfileByUsername(
            @PathVariable String username,
            @RequestHeader("Authorization") String authHeader){

        return ResponseEntity.ok(followService
                .unfollowProfileByUsername(
                        username,
                        jwtService.getUsernameFromRequestHeader(authHeader)
                )
        );
    }
}
