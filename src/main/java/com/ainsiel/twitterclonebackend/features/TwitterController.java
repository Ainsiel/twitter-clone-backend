package com.ainsiel.twitterclonebackend.features;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TwitterController {

    @PostMapping("/hello")
    public String hello(){
        return "Hello world from secured endpoint";
    }
}
