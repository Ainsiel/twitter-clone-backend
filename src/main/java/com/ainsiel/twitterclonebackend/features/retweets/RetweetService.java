package com.ainsiel.twitterclonebackend.features.retweets;


import com.ainsiel.twitterclonebackend.features.tweets.TweetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetweetService {


    public Boolean isTweetRetweetedByUsername(TweetEntity tweet, String username) {

        return false;
    }

    public Integer getTweetTotalRetweets(TweetEntity tweet) {

        return 0;
    }
}
