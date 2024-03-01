package com.ainsiel.twitterclonebackend.features.replies;


import com.ainsiel.twitterclonebackend.features.tweets.TweetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {


    public TweetEntity getParentByTweetId(Integer id) {

        return null;
    }

    public List<TweetEntity> getRepliesByTweetId(Integer id) {

        return null;
    }

    public Integer getTweetTotalReplies(TweetEntity tweet) {

        return 0;
    }
}
