package com.ainsiel.twitterclonebackend.features.tweets;

import com.ainsiel.twitterclonebackend.features.replies.ReplyRequest;
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
                tweetService
                        .getRandomTweetsOrderByTweetedAt(
                                jwtService.getUsernameFromRequestHeader(authHeader))
        );
    }

    @GetMapping("/Following")
    public ResponseEntity<List<TweetResponse>> getFollowingTweets(
            @RequestHeader("Authorization") String authHeader){

        return ResponseEntity.ok(
                tweetService
                        .getFollowingTweetsOrderByTweetedAt(
                                jwtService.getUsernameFromRequestHeader(authHeader))
        );
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<TweetResponse>> getTweetsByUsername(
            @PathVariable String username,
            @RequestHeader("Authorization") String authHeader){

        return ResponseEntity.ok(
                tweetService.getTweetsByUsernameOrderByTweetedAt(
                        username,
                        jwtService.getUsernameFromRequestHeader(authHeader))
        );
    }

    @PostMapping
    public ResponseEntity<TweetResponse> postTweet(
            @RequestBody TweetRequest tweet,
            @RequestHeader("Authorization") String authHeader){

        return ResponseEntity.ok(
                tweetService
                        .createTweet(
                                tweet,
                                jwtService.getUsernameFromRequestHeader(authHeader))
        );
    }

    @PostMapping("/replies")
    public ResponseEntity<TweetResponse> postReply(
            @RequestBody ReplyRequest reply,
            @RequestHeader("Authorization") String authHeader) {

        return ResponseEntity.ok(tweetService
                .createReply(
                        reply,
                        jwtService.getUsernameFromRequestHeader(authHeader))
        );
    }

    @GetMapping("/replies/{username}")
    public ResponseEntity<List<TweetResponse>> getProfileReplies(
            @PathVariable String username,
            @RequestHeader("Authorization") String authHeader) {

        return ResponseEntity.ok(tweetService
                .getAllProfileRepliesTweetsOrderByTweetedAt(
                        username,
                        jwtService.getUsernameFromRequestHeader(authHeader))
        );
    }
}
