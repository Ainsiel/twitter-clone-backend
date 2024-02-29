package com.ainsiel.twitterclonebackend.features.tweets;

import com.ainsiel.twitterclonebackend.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tweets")
@RequiredArgsConstructor
public class TweetController {

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final TweetService tweetService;


    @GetMapping("/{id}")
    public ResponseEntity<TweetDetailsResponse> getTweetById(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String authHeader){

        return ResponseEntity.ok(tweetService.getTweetDetailsById(
                id,
                jwtService.getUsernameFromRequestHeader(authHeader)
        ));
    }

    @GetMapping("/ForYou")
    public ResponseEntity<List<TweetResponse>> getForYouTweets(
            @RequestHeader("Authorization") String authHeader){

        return ResponseEntity.ok(
                tweetService.getRandomTweetsOrderByTweetedAt(authHeader)
        );
    }

    @GetMapping("/Following")
    public ResponseEntity<List<TweetResponse>> getFollowingTweets(
            @RequestHeader("Authorization") String authHeader){

        return ResponseEntity.ok(
                tweetService.getFollowingTweetsOrderByTweetedAt(authHeader)
        );
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<TweetResponse>> getTweetsByUsername(
            @PathVariable String username,
            @RequestHeader("Authorization") String authHeader){

        return ResponseEntity.ok(
                tweetService.getTweetsByUsernameOrderByTweetedAt(
                        username,
                        authHeader)
        );
    }

    @PostMapping
    public ResponseEntity<TweetResponse> postTweet(
            @RequestBody TweetRequest tweet,
            @RequestHeader("Authorization") String authHeader){

        return ResponseEntity.ok(
                tweetService.createTweet(tweet,authHeader)
        );
    }
}
