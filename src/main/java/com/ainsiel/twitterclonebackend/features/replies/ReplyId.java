package com.ainsiel.twitterclonebackend.features.replies;


import com.ainsiel.twitterclonebackend.features.tweets.TweetEntity;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ReplyId {

    @ManyToOne
    @JoinColumn(name = "tweet", referencedColumnName = "id", nullable = false)
    private TweetEntity tweet;

    @ManyToOne
    @JoinColumn(name = "reply", referencedColumnName = "id", nullable = false)
    private TweetEntity reply;
}
