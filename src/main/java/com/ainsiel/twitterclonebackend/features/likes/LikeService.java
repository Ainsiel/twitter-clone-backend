package com.ainsiel.twitterclonebackend.features.likes;


import com.ainsiel.twitterclonebackend.features.tweets.TweetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    public Boolean isTweetLikedByUsername(TweetEntity tweet, String username) {

        return false;
    }

    public Integer getTweetTotalLikes(TweetEntity tweet) {

        return 0;
    }
}
