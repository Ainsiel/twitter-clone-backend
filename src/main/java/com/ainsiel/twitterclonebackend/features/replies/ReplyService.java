package com.ainsiel.twitterclonebackend.features.replies;


import com.ainsiel.twitterclonebackend.features.tweets.TweetEntity;
import com.ainsiel.twitterclonebackend.features.tweets.TweetResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    @Autowired
    private final IReplyRepository replyRepository;


    public TweetEntity getParentByTweetId(Integer id) {

        ReplyEntity tweet = replyRepository
                .findByReply_Reply_Id(id)
                .orElse(null);


        return tweet != null ? tweet.getReply().getTweet() : null;
    }

    public List<TweetEntity> getRepliesByTweetId(Integer id) {

        List<ReplyEntity> replies = replyRepository
                .findAllByReply_Tweet_Id(id).orElse(Collections.emptyList());

        return replies.stream().map(reply -> reply.getReply().getReply()).toList();
    }

    public Integer getTweetTotalReplies(TweetEntity tweet) {

        return (int) replyRepository.countByReply_Tweet_Id(tweet.getId());
    }

    public ReplyEntity saveReply(
            TweetEntity tweet,
            TweetEntity reply) {

        return replyRepository.save(new ReplyEntity(
                new ReplyId(tweet,reply)
        ));
    }

    public List<ReplyEntity> getAllRepliesByUsername(String username) {

        return replyRepository
                .findAllByReply_Reply_Profile_Username(username)
                .orElse(Collections.emptyList());
    }
}
