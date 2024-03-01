package com.ainsiel.twitterclonebackend.features.retweets;



import com.ainsiel.twitterclonebackend.features.profiles.ProfileEntity;
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
public class RetweetId {

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false)
    private ProfileEntity profile;

    @ManyToOne
    @JoinColumn(name = "tweet_id", referencedColumnName = "id", nullable = false)
    private TweetEntity tweet;
}
