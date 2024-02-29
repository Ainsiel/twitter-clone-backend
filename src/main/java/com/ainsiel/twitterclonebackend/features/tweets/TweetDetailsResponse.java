package com.ainsiel.twitterclonebackend.features.tweets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TweetDetailsResponse {

    private Integer id;
    private String profileAvatar;
    private String profileName;
    private String profileUsername;
    private String tweetedAt;
    private String content;
    private Boolean isRetweeted;
    private Boolean isLiked;
    private Integer replies;
    private Integer retweets;
    private Integer likes;
    private Integer analytics;

    private TweetResponse parent;
    private List<TweetResponse> repliesTweets;
}
