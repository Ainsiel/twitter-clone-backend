package com.ainsiel.twitterclonebackend.features.retweets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IRetweetRepository extends JpaRepository<RetweetEntity, RetweetId> {

    Optional<List<RetweetEntity>> findAllByRetweet_Profile_Username(String username);

    long countByRetweet_Tweet_Id(Integer tweetId);

    boolean existsByRetweet_Profile_UsernameAndRetweet_Tweet_Id(
            String username, Integer tweetId);

    void deleteByRetweet_Profile_UsernameAndRetweet_Tweet_Id(String profileUsername, Integer tweetId);
}
