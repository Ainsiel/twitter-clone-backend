package com.ainsiel.twitterclonebackend.features.likes;


import com.ainsiel.twitterclonebackend.features.profiles.ProfileEntity;
import com.ainsiel.twitterclonebackend.features.retweets.RetweetEntity;
import com.ainsiel.twitterclonebackend.features.retweets.RetweetId;
import com.ainsiel.twitterclonebackend.features.tweets.TweetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    @Autowired
    private final ILikeRepository likeRepository;

    public List<LikeEntity> getAllLikesByUsername(String username){

        return likeRepository
                .findAllByLike_Profile_Username(username)
                .orElse(Collections.emptyList());
    }

    public LikeEntity createLike(ProfileEntity profile, TweetEntity tweet){

        return likeRepository
                .save(LikeEntity.builder()
                        .like(LikeId.builder()
                                .profile(profile)
                                .tweet(tweet)
                                .build())
                        .likedAt(LocalDate.now())
                        .build());
    }

    public Boolean isTweetLikedByUsername(TweetEntity tweet, String username) {

        return likeRepository
                .existsByLike_Profile_UsernameAndLike_Tweet_Id(username, tweet.getId());
    }

    public Integer getTweetTotalLikes(TweetEntity tweet) {

        return (int) likeRepository.countByLike_Tweet_Id(tweet.getId());
    }
}
