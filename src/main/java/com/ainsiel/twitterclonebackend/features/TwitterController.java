package com.ainsiel.twitterclonebackend.features;


import com.ainsiel.twitterclonebackend.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TwitterController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/hello")
    public String hello(@RequestHeader("Authorization") String authHeader){
        return "Hello world from secured endpoint: "
                .concat(jwtService.getUsernameFromRequestHeader(authHeader));
    }
}
