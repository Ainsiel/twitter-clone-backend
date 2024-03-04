package com.ainsiel.twitterclonebackend.features.retweets;


import com.ainsiel.twitterclonebackend.features.profiles.ProfileEntity;
import com.ainsiel.twitterclonebackend.features.tweets.TweetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RetweetService {

    @Autowired
    private IRetweetRepository retweetRepository;

    public List<RetweetEntity> getAllRetweetsByUsername(String username){

        return retweetRepository
                .findAllByRetweet_Profile_Username(username)
                .orElse(Collections.emptyList());
    }

    public RetweetEntity createRetweet(ProfileEntity profile, TweetEntity tweet){

        return retweetRepository
                .save(RetweetEntity.builder()
                        .retweet(RetweetId.builder()
                                .profile(profile)
                                .tweet(tweet)
                                .build())
                        .retweetedAt(LocalDate.now())
                        .build());
    }


    public Boolean isTweetRetweetedByUsername(TweetEntity tweet, String username) {

        return retweetRepository
                .existsByRetweet_Profile_UsernameAndRetweet_Tweet_Id(username, tweet.getId());
    }

    public Integer getTweetTotalRetweets(TweetEntity tweet) {

        return (int) retweetRepository.countByRetweet_Tweet_Id(tweet.getId());
    }
}
